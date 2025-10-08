package com.example.Porter.OAuth;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;
import com.example.Porter.Customer.CustomerLoginHistoryModel;
import com.example.Porter.Customer.CustomerLoginHistoryRepository;
import com.example.Porter.Customer.CustomerModel;
import com.example.Porter.Customer.CustomerModel.LoginType;
import com.example.Porter.Customer.CustomerRepository;
import com.example.Porter.Exception.FieldValidException;
import com.example.Porter.Location.LocationModel;
import com.example.Porter.Location.LocationRepository;
import com.example.Porter.Responsehandler.SuccessResponseHandler;
import com.example.Porter.Security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerOAuthService {


	private final CustomerRepository customerRepo;
	private final JwtService jwtService;
	private final CustomerLoginHistoryRepository loginRepo;
	private final LocationRepository locationRepo;
	
	public ResponseEntity<APIResponseDto<CustomerDto>> oauthLogin(OAuth2User principal, HttpServletRequest request) {
		String email = principal.getAttribute("email");
		String name = principal.getAttribute("name");
		
		LocationModel defaultLocation = locationRepo.findById(1L) 
			    .orElseThrow(() -> new FieldValidException("Default location not found"));

		Optional<CustomerModel> customer = customerRepo.findByEmailAddress(email);
		CustomerModel newCustomer = null;
		if (customer.isEmpty()) {
			newCustomer = new CustomerModel();
			newCustomer.setEmailAddress(email);
			newCustomer.setCustomerName(name);
			newCustomer.setLoginType(LoginType.GOOGLE);
			newCustomer.setCreatedBy("Googel_auth");
			newCustomer.setMobileNumber("9025562886");
			newCustomer.setLastLoginDateTime(LocalDateTime.now());
			newCustomer.setResetDateTime(LocalDateTime.now());
			newCustomer.setFailedLoginCount(0);
			newCustomer.setLogOutStatus(false);
			newCustomer.setLocation(defaultLocation);
			customerRepo.save(newCustomer);

		} else {
			if (customer.get().getLoginType() != LoginType.GOOGLE) {
				throw new FieldValidException(
						"This email is already registered using Email / Password, please try to login using password");
			}
		}
		
	    CustomerModel customerToUse = customer.orElse(newCustomer);


		String token = jwtService.generateToken(email);
		CustomerLoginHistoryModel history = CustomerLoginHistoryModel.builder().loginTime(LocalDateTime.now())
				.loginStatus("Google OAuth Login").ipAddress(request.getRemoteAddr()).customer(customerToUse).build();
		loginRepo.save(history);

		CustomerDto dto = new CustomerDto();
		BeanUtils.copyProperties(customerToUse, dto);
		dto.setJwtToken(token);

		return ResponseEntity.ok(SuccessResponseHandler.success(dto, "Customer details fetched by status"));
	}

}
