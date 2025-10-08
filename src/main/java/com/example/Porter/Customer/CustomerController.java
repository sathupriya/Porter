package com.example.Porter.Customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.CustomerDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

	private final CustomerService service;

	
	@PostMapping("/addCustomer")
	public ResponseEntity<APIResponseDto<CustomerDto>> addCustomer(@RequestBody CustomerDto dto) {
		return service.addCustomer(dto);
	}

	@PatchMapping("/updateCustomer")
	public ResponseEntity<APIResponseDto<CustomerDto>> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto dto) {
		return service.updateCustomer(id, dto);
	}

	@PatchMapping("/updateStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<CustomerDto>> updateStatus(@PathVariable Long id, @PathVariable boolean status) {
		return service.updateStatus(id, status);
	}

	@GetMapping("/viewById/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> viewById(@PathVariable Long id) {
		return service.viewById(id);
	}

	@GetMapping("/getAllCustomer/{page}/{pageSize}")
	public ResponseEntity<APIResponseDto<Page<CustomerDto>>> getAllCustomer(@PathVariable int page, @PathVariable int pageSize) {
		return service.getAllCustomer(page, pageSize);
	}

	@PostMapping("/customerLogin")
	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogin(@RequestBody CustomerDto dto,HttpServletRequest request) {
		return service.customerLogin(dto,request);
	}

	@GetMapping("/customerLogout/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> customerLogout(@PathVariable Long id) {
		return service.customerLogout(id);
	}

	@PutMapping("/emailVerify/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> emailVerify(@PathVariable Long id,@RequestBody CustomerDto dto){
		return service.emailVerify(id,dto);
	}

	@GetMapping("/sendOtp/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> sendOtpEmail(@PathVariable Long id){
		return service.sendOtpEmail(id);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPassword(@RequestBody CustomerDto dto){
		return service.forgotPassword(dto);
	}

	@GetMapping("/forgotPasswordMail/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> forgotPasswordMail(@PathVariable Long id){
		return service.forgotPasswordMail(id);
	}

	@PutMapping("/changePassword")
	public ResponseEntity<APIResponseDto<CustomerDto>> changePassword(@RequestBody CustomerDto dto){
		return service.changePassword(dto);
	}

	@GetMapping("/viewByStatus/{status}")
	public ResponseEntity<APIResponseDto<List<CustomerDto>>> viewByActive(@PathVariable boolean status){
		return service.viewByActive(status);
	}

	@PostMapping("/changeEmailAddress")
	public ResponseEntity<APIResponseDto<CustomerDto>> changeEmailAddress(@RequestBody CustomerDto dto){
		return service.changeEmailAddress(dto);
	}

	@PostMapping("/changeMobileNumber")
	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileNumber(@RequestBody CustomerDto dto){
		return service.changeMobileNumber(dto);
	}

	@GetMapping("/changeMobileOrEmail/{id}")
	public ResponseEntity<APIResponseDto<CustomerDto>> changeMobileOrEmailMail(@PathVariable Long id){
		return service.changeMobileOrEmailMail(id);
	}
	
	

}
