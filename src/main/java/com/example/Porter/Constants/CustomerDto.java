package com.example.Porter.Constants;

import java.time.LocalDateTime;

import com.example.Porter.Customer.CustomerModel.LoginType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

	private Long customerId;
	private String customerName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String mobileNumber;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String emailAddress;
	private String dateOfBirth;
	private String userPassword;
	@JsonIgnore
	private String hashedPassword;
	private boolean isActive;
	private String address;
	private String profilePicture;
	private String createdBy;
	private String modifiedBy;
	private boolean emailVerificationStatus;
	private String otpCode;
	private boolean logOutStatus;
	private String oldPassword;
	private String newPassword;
	private String oldMobileNumber;
	private String newMobilenumber;
	private String newEmailAddress;
	private String oldEmailAddress;
	private String jwtToken;
	private Long customerLoginId;
	private LocalDateTime loginTime;
	private LocalDateTime logoutTime;
	private String ipAddress;
	private String loginStatus;
	private String sessionDuration;
	private boolean forgotPasswordVerifiedStatus;
	private String reasonForChange;
	private int flag;
	private Long locationId;
	private LoginType loginType;
	
	
	
}
