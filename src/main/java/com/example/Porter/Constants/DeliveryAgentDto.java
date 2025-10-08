package com.example.Porter.Constants;

import java.time.LocalDateTime;

import com.example.Porter.DeliveryAgent.DeliveryAgentModel.AgentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryAgentDto {

	private Long deliveryAgentId;
	private String deliveryAgentName;
	private String mobileNumber;
	@Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address")
	private String emailAddress;
	@NotBlank
	@Size(min = 12, max = 15, message = "Aadhar number must be 12 digits")
	private String aadharNumber;
	@NotBlank
	@Size(min = 12, max = 15, message = "License number is invalid")
	private String licenseNumber;
	private String vehicleNumber;
	private String vehicleType;
	private String aadharImageUrl;
	private String licenseImageUrl;
	private String profileImageUrl;
	private Long locationId;
	private AgentStatus status;

	private boolean isActive;
	private String createdBy;
	private String modifiedBy;

	private String currentLattitude;
	private String currentLongitude;
	private String hashedPassword;
	private String dateOfBirth;
	private String address;

	private boolean emailVerificationStatus;
	private LocalDateTime otpVerifiedat;
	private LocalDateTime otpSendon;
	private boolean mobileVerificationStatus;
	private LocalDateTime mobileOtpVerifiedat;
	private LocalDateTime mobileOtpSendon;
	private LocalDateTime failedLoginDate;
	private int failedLoginCount;
	private LocalDateTime lastLoginDate;
	private String userPassword;
	private String newPassword;
	private String oldPassword;

}
