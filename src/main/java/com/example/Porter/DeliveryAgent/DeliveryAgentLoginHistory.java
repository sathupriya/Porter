package com.example.Porter.DeliveryAgent;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.Porter.Customer.CustomerLoginHistoryModel;
import com.example.Porter.Customer.CustomerModel;
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
@Table(name="deliveryagent_login_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAgentLoginHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="customer_login_id")
	private Long deliveryAgentLoginId;
	
	private LocalDateTime loginTime;
	
	private LocalDateTime logoutTime;
	
	private String ipAddress;
	
	private String loginStatus;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	private String sessionDuration;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name="delivery_agent_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private DeliveryAgentModel deliveryAgent;
	
}
