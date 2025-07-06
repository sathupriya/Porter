package com.example.Porter.Customer;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customer_password_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPasswordHistoryModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cus_password_id")
	private Long customerPasswordId;
	
	@Column(name="current_password_status")
	private boolean currentPasswordStatus;
	
	@Column(name="hashed_password")
	private String hashedPassword;
	
	@Column(name="reason_for_change")
	private String reasonForChange;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="modifed_at")
	private LocalDateTime modifiedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="customer_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private CustomerModel customer;
	
	
}
