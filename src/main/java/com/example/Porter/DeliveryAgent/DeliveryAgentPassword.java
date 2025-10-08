package com.example.Porter.DeliveryAgent;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="deliveryagent_password_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentPassword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agentPasswordId;
	
	private boolean currentPasswordStatus;
	
	private String hashedPassword;
	
	private String reasonForChange;
	
	private String createdBy;
	
	private String modifiedBy;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="delivery_agent_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private DeliveryAgentModel deliveryAgent;
	
}
