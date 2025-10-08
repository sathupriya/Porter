package com.example.Porter.DeliveryAgent;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryAgentLoginHistoryRepository extends JpaRepository<DeliveryAgentLoginHistory,Long>{

	Optional<DeliveryAgentLoginHistory> findTopByCustomerDeliveryAgentIdOrderByDeliveryAgentLoginIdDesc(Long customerId);

}
