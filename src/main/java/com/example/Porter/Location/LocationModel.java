package com.example.Porter.Location;

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
@Table(name="location_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="location_id")
	private Long locationId;
	
	@Column(name="pincode")
	private String pincode;
	
	@Column(name="landmark")
	private String landmark;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@CreationTimestamp
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name="modified_at")
	private LocalDateTime modifiedAt;
	
	@Column(name="status")
	private boolean activeStatus;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="city_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private CityModel city;
	
}
