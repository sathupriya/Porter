package com.example.Porter.Customer;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;
import com.example.Porter.Customer.CustomerModel.LoginType;
import com.example.Porter.Email.EmailService;
import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Exception.FieldValidException;
import com.example.Porter.Exception.ResourceNotFoundException;
import com.example.Porter.Location.LocationModel;
import com.example.Porter.Location.LocationRepository;
import com.example.Porter.Responsehandler.SuccessResponseHandler;
import com.example.Porter.Security.JwtService;
import com.example.Porter.Security.SecurityConfig;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepo;
	private final SecurityConfig security;
	private final CustomerPasswordHistoryRepository passwordRepo;
	private final JwtService jwtService;
	private final CustomerLoginHistoryRepository loginRepo;
	private final EmailService service;
	private final LocationRepository locationRepo;

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> addCustomer(CustomerDto dto) {
		Optional<CustomerModel> cusEmail = customerRepo.findByEmailAddress(dto.getEmailAddress());
		Optional<CustomerModel> cusMobile = customerRepo.findByMobileNumber(dto.getMobileNumber());
		LocationModel location = locationRepo.findById(dto.getLocationId())
				.orElseThrow(() -> new ResourceNotFoundException("Location not found"));

		if (cusEmail.isPresent() && cusMobile.isPresent()) {
			throw new AlreadyExistsException(
					cusEmail.isPresent() ? "Email address already present, Please try with another email address"
							: "Mobile number already present, Please try with another mobile number");
		}

		String hashedPassword = security.passwordEncoder().encode(dto.getUserPassword());

		CustomerModel customer = CustomerModel.builder().address(dto.getAddress()).createdBy(dto.getCreatedBy())
				.customerName(dto.getCustomerName()).dateOfBirth(dto.getDateOfBirth()).hashedPassword(hashedPassword)
				.emailAddress(dto.getEmailAddress()).mobileNumber(dto.getMobileNumber()).location(location).build();
		CustomerModel repo = customerRepo.save(customer);

		CustomerPasswordHistoryModel password = CustomerPasswordHistoryModel.builder().currentPasswordStatus(true)
				.hashedPassword(hashedPassword).customer(customer).build();

		passwordRepo.save(password);

		CustomerDto saved = new CustomerDto();

		BeanUtils.copyProperties(repo, saved);
		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Customer addedd successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> updateCustomer(Long id, CustomerDto dto) {
		Optional<CustomerModel> customer = customerRepo.findById(id);
		if (customer.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Customer details not found"));
		}

		CustomerModel customerDetails = customer.get();
		customerDetails.setAddress(dto.getAddress());
		customerDetails.setModifiedBy(dto.getModifiedBy());
		customerDetails.setCustomerName(dto.getCustomerName());
		customerDetails.setDateOfBirth(dto.getDateOfBirth());

		CustomerModel saved = customerRepo.save(customerDetails);

		CustomerDto response = new CustomerDto();
		BeanUtils.copyProperties(saved, response);

		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Customer details updated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> updateStatus(Long id, boolean status) {
		Optional<CustomerModel> customer = customerRepo.findById(id);

		if (customer.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		CustomerModel customerDetails = customer.get();
		customerDetails.setActive(status);
		customerRepo.save(customerDetails);
		return ResponseEntity
				.ok(SuccessResponseHandler.success(status == true ? "Customer account activated successfully"
						: "Customer account de-activated successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> viewById(Long id) {
		Optional<CustomerModel> customer = customerRepo.findById(id);

		if (customer.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("No data found"));
		}

		CustomerModel customerDetails = customer.get();
		CustomerDto saved = new CustomerDto();
		BeanUtils.copyProperties(customerDetails, saved);
		return ResponseEntity.ok(SuccessResponseHandler.success(saved, "Customer details fetched successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<Page<CustomerDto>>> getAllCustomer(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<CustomerModel> content = customerRepo.findAllByOrderByCustomerIdDesc(pageable);

		if (content.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("No data found"));

		}

		Page<CustomerDto> response = content.map(model -> {
			CustomerDto saved = new CustomerDto();
			BeanUtils.copyProperties(model, saved);
			return saved;

		});
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "All Customer details fetched succesfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogin(CustomerDto dto, HttpServletRequest request) {
		Optional<CustomerModel> customer = customerRepo.findByEmailAddress(dto.getEmailAddress());

		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Email address not found");
		}

		CustomerModel details = customer.get();

		boolean passwordMatch = security.passwordEncoder().matches(dto.getUserPassword(), details.getHashedPassword());

		if (!passwordMatch) {
			details.setFailedLoginCount(details.getFailedLoginCount() + 1);
			details.setFailedLoginDate(LocalDateTime.now());
			customerRepo.save(details);
			throw new FieldValidException("Incorrect password, please try with correct password");
		}

		String token = jwtService.generateToken(details.getEmailAddress());

		details.setResetDateTime(LocalDateTime.now());
		details.setLastLoginDateTime(LocalDateTime.now());
		details.setFailedLoginCount(0);
		details.setLogOutStatus(false);
		details.setLoginType(LoginType.PASSWORD);
		customerRepo.save(details);

		CustomerLoginHistoryModel history = CustomerLoginHistoryModel.builder().loginTime(LocalDateTime.now())
				.loginStatus("Logging In").ipAddress(request.getRemoteAddr()).customer(details).build();
		loginRepo.save(history);
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(details, customerDto);
		customerDto.setJwtToken(token);

		return ResponseEntity.ok(SuccessResponseHandler.success(customerDto, "Login successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogout(Long id) {
		Optional<CustomerModel> customer = customerRepo.findById(id);
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found");
		}

		CustomerModel details = customer.get();

		if (details.isLogOutStatus()) {
			throw new AlreadyExistsException("Customer already logged out");
		}

		details.setLogOutStatus(true);
		customerRepo.save(details);

		Optional<CustomerLoginHistoryModel> lastLogin = loginRepo
				.findTopByCustomerCustomerIdOrderByCustomerLoginIdDesc(details.getCustomerId());

		if (lastLogin.isPresent()) {
			CustomerLoginHistoryModel login = lastLogin.get();
			LocalDateTime loginTime = login.getLoginTime();
			LocalDateTime logoutTime = LocalDateTime.now();
			login.setLogoutTime(logoutTime);
			login.setLoginStatus("Logged out");

			Duration duration = Duration.between(loginTime, logoutTime);
			Long hours = duration.toHours();
			Long minutes = duration.toMinutes() % 60;
			Long seconds = duration.toSeconds() % 60;
			Long millis = duration.toMillis() % 1000;

			String formatDuration = (hours > 0) ? String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, millis)
					: String.format("%02d:%02d.%03d", minutes, seconds, millis);

			login.setSessionDuration(formatDuration);
			loginRepo.save(login);

		}

		CustomerDto response = new CustomerDto();
		BeanUtils.copyProperties(details, response);
		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Customer logged out successfully"));
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> emailVerify(Long id, CustomerDto dto) {
		var customer = customerRepo.findById(id);
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found");
		}

		CustomerModel details = customer.get();
		if (!details.getOtpCode().equals(dto.getOtpCode())) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Invalid OTP!"));
		}

		if (details.getEmailOtpSendOn().plusMinutes(3).isBefore(LocalDateTime.now())) {
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP got expired, Please resend the OTP!"));

		}

		switch (dto.getFlag()) {
		case 1:
			details.setEmailVerificationStatus(true);
			details.setActive(true);
			details.setOtpVerifiedAt(LocalDateTime.now());
			customerRepo.save(details);
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP verified successfully"));

		case 2:

			details.setForgotPasswordVerifiedStatus(true);
			details.setOtpVerifiedAt(LocalDateTime.now());
			customerRepo.save(details);
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP verified successfully"));

		default:

			throw new FieldValidException("Unsupported flag");

		}

	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> sendOtpEmail(Long id) {
		Optional<CustomerModel> customer = customerRepo.findById(id);
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("customer not found");

		}

		CustomerModel details = customer.get();

		SecureRandom random = new SecureRandom();
		String otp = String.valueOf(random.nextInt(900000) + 100000);
		boolean mail = service.emailVerify(details.getEmailAddress(), otp, details.getCustomerName());

		if (mail) {
			details.setOtpCode(otp);
			details.setEmailOtpSendOn(LocalDateTime.now());
			details.setEmailVerificationStatus(false);
			customerRepo.save(details);
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP has been sent successfully"));

		} else {
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP not sent"));

		}

	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPassword(CustomerDto dto) {
		Optional<CustomerModel> customer = customerRepo.findByEmailAddress(dto.getEmailAddress());
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Email address not found");

		}

		CustomerModel details = customer.get();

		if (!details.isForgotPasswordVerifiedStatus()) {
			return ResponseEntity
					.ok(SuccessResponseHandler.success("Please verify the OTP before changing the password!"));

		}

		var password = passwordRepo.findByCustomerCustomerIdAndCurrentPasswordStatus(details.getCustomerId(), true);

		password.setCurrentPasswordStatus(false);
		passwordRepo.save(password);

		String hashedPassword = security.passwordEncoder().encode(dto.getUserPassword());

		CustomerPasswordHistoryModel passHis = new CustomerPasswordHistoryModel();
		passHis.setCustomer(details);
		passHis.setHashedPassword(hashedPassword);
		passHis.setCurrentPasswordStatus(true);
		passHis.setReasonForChange("Forgot password");
		passwordRepo.save(passHis);

		details.setHashedPassword(hashedPassword);
		details.setForgotPasswordVerifiedStatus(false);
		customerRepo.save(details);

		return ResponseEntity.ok(SuccessResponseHandler.success("Password updated successfully!"));

	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPasswordMail(Long id) {
		Optional<CustomerModel> customer = customerRepo.findById(id);
		if (customer.isEmpty()) {
			throw new ResourceNotFoundException("Customer not found");

		}

		CustomerModel details = customer.get();

		SecureRandom random = new SecureRandom();
		String otp = String.valueOf(random.nextInt(900000) + 100000);
		boolean mail = service.forgotMail(details.getEmailAddress(), otp, details.getCustomerName());

		if (mail) {
			details.setOtpCode(otp);
			details.setEmailOtpSendOn(LocalDateTime.now());
			details.setForgotPasswordVerifiedStatus(false);
			customerRepo.save(details);
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP has been sent successfully"));

		} else {
			return ResponseEntity.ok(SuccessResponseHandler.success("OTP not sent"));

		}
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> changePassword(CustomerDto dto) {
		Optional<CustomerModel> customer = customerRepo.findById(dto.getCustomerId());
		if (customer.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.success("Customer not found"));

		}

		CustomerModel details = customer.get();

		String hashedPassword = security.passwordEncoder().encode(dto.getNewPassword());
		if (!security.passwordEncoder().matches(dto.getOldPassword(), details.getHashedPassword())) {
			throw new FieldValidException("Old password is incorrect");
		}

		if (dto.getOldPassword().matches(dto.getNewPassword())) {
			throw new FieldValidException("New password should not be same as Old password");
		}

		CustomerPasswordHistoryModel existPassword = passwordRepo
				.findByCustomerCustomerIdAndCurrentPasswordStatus(dto.getCustomerId(), true);
		existPassword.setCurrentPasswordStatus(false);
		passwordRepo.save(existPassword);

		CustomerPasswordHistoryModel newPassword = CustomerPasswordHistoryModel.builder().customer(details)
				.hashedPassword(hashedPassword).currentPasswordStatus(true).reasonForChange("Change password").build();
		passwordRepo.save(newPassword);
		details.setHashedPassword(hashedPassword);
		customerRepo.save(details);

		return ResponseEntity.ok(SuccessResponseHandler.success("Password changes successfully"));

	}

	@Override
	public ResponseEntity<APIResponseDto<List<CustomerDto>>> viewByActive(boolean status) {
		List<CustomerModel> customer = customerRepo.findByIsActive(status);
		if (customer.isEmpty()) {
			return ResponseEntity.ok(SuccessResponseHandler.noDataFound("No data found"));
		}

		List<CustomerDto> response = customer.stream().map(model -> {
			CustomerDto saved = new CustomerDto();
			BeanUtils.copyProperties(model, saved);
			return saved;
		}).toList();

		return ResponseEntity.ok(SuccessResponseHandler.success(response, "Customer details fetched by status"));
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> changeEmailAddress(CustomerDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileNumber(CustomerDto dto) {
		return null;
	}

	@Override
	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileOrEmailMail(Long id) {
		return null;
	}

	
}
