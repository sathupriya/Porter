package com.example.Porter.DeliveryAgent;

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
@Table(name = "delivery_agent_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAgentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliveryAgentId;
	@Column(nullable = false)
	private String deliveryAgentName;
	@Column(unique = true)
	private String mobileNumber;
	@Column(unique = true)
	private String emailAddress;
	private String profileImageUrl;
	@Column(nullable = false)
	private String hashedPassword;
	private String dateOfBirth;
	private String address;
	
	
	@Enumerated(EnumType.STRING)
	private AgentStatus status;

	@Builder.Default
	private boolean isActive = false;
	@Builder.Default
	private boolean emailVerificationStatus = false;
	private LocalDateTime otpVerifiedat;
	private LocalDateTime otpSendon;
	@Builder.Default
	private boolean mobileVerificationStatus = false;
	private LocalDateTime mobileOtpVerifiedat;
	private LocalDateTime mobileOtpSendon;
	private LocalDateTime failedLoginDate;
	private int failedLoginCount;
	private LocalDateTime lastLoginDate;
	
	@Builder.Default
	private boolean logOutStatus = false;
	private LocalDateTime resetDateTime;
	@Builder.Default
	private boolean forgotPasswordVerifiedStatus = false;
	
	
	private String createdBy;
	private String modifiedBy;

	private String currentLattitude;
	private String currentLongitude;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
	@Builder.Default
	@OneToMany(mappedBy="deliveryAgent",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<DeliveryAgentPassword> deliveryAgent = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="location_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private LocationModel location;

	public  enum AgentStatus {
		PENDING, APPROVED, REJECTED
	}

}
