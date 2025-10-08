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
@Table(name="state_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateModel {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="state_id")
	private Long stateId;
	
	@Column(name="state_name",nullable=false)
	private String stateName;
	
	@Column(name="state_code")
	private String stateCode;
	
	@Column(name="status")
	private boolean activeStatus;
	
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="country_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private CountryModel country;
	
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
