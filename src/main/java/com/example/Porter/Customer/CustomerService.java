package com.example.Porter.Customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {

	public ResponseEntity<APIResponseDto<CustomerDto>> addCustomer(CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> updateCustomer(Long id, CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> updateStatus(Long id, boolean status);

	public ResponseEntity<APIResponseDto<CustomerDto>> viewById(Long id);

	public ResponseEntity<APIResponseDto<Page<CustomerDto>>> getAllCustomer(int page, int pageSize);

	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogin(CustomerDto dto, HttpServletRequest request);

	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogout(Long id);

	public ResponseEntity<APIResponseDto<CustomerDto>> emailVerify(Long id, CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> sendOtpEmail(Long id);

	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPassword(CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPasswordMail(Long id);

	public ResponseEntity<APIResponseDto<CustomerDto>> changePassword(CustomerDto dto);

	public ResponseEntity<APIResponseDto<List<CustomerDto>>> viewByActive(boolean status);

	public ResponseEntity<APIResponseDto<CustomerDto>> changeEmailAddress(CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileNumber(CustomerDto dto);

	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileOrEmailMail(Long id);


}
