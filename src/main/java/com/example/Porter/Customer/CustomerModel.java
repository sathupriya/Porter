package com.example.Porter.Customer;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.Porter.Location.LocationModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "mobile_number", nullable = false,unique = true)
	private String mobileNumber;

	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "date_of_birth")
	private String dateOfBirth;
	
	@Column(name="hashed_password")
	private String hashedPassword;

	@Builder.Default
	@Column(name = "active_status")
	private boolean isActive = false;

	@Column(name = "customer_address")
	private String address;

	@Column(name = "profile_picture")
	private String profilePicture;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modifed_by")
	private String modifiedBy;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "modified_at")
	private LocalDateTime modifiedAt;

	@Builder.Default
	@Column(name = "email_verification_status")
	private boolean emailVerificationStatus = false;

	@Column(name = "otp_code")
	private String otpCode;

	@Column(name = "email_otpcode_sendon")
	private LocalDateTime emailOtpSendOn;

	@Column(name = "email_otpverifiedat")
	private LocalDateTime otpVerifiedAt;

	@Column(name = "failed_logindate_time")
	private LocalDateTime failedLoginDate;

	@Column(name = "failed_login_count")
	private int failedLoginCount;

	@Column(name = "last_login_datetime")
	private LocalDateTime lastLoginDateTime;

	@Builder.Default
	@Column(name = "log_out_status")
	private boolean logOutStatus = false;
	
	@Column(name="reset_date_time")
	private LocalDateTime resetDateTime;
	
	@Builder.Default
	@Column(name="forgotpass_otp_verifiedstatus")
	private boolean forgotPasswordVerifiedStatus = false;
	
	@Enumerated(EnumType.STRING)
	private LoginType loginType;
	

	@Builder.Default
	@OneToMany(mappedBy="customer",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<CustomerPasswordHistoryModel> customerPassword = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="location_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private LocationModel location;
	
	public enum LoginType{
		GOOGLE,PASSWORD
	}
}
