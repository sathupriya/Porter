package com.example.Porter.Location;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@EntityListeners(AuditingEntityListener.class)
@Table(name="country_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="country_id")
	private Long countryId;
	
	@Column(name="country_name",nullable=false,unique = true)
	private String countryName;
	
	@Column(name="country_code", unique = true)
	private String countryCode;
	
	@Column(name="status")
	private boolean activeStatus;
	
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
}
