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
@Table(name="city_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="city_id")
	private Long cityId;
	
	@Column(name="city_name",nullable=false)
	private String cityName;
	
	@Column(name="city_code")
	private String cityCode;
	
	@Column(name="status")
	private boolean activeStatus;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="state_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private StateModel state;
	
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
