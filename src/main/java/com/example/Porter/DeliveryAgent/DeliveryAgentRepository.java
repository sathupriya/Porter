package com.example.Porter.DeliveryAgent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgentModel, Long> {

	Optional<DeliveryAgentModel> findByMobileNumber(String mobileNumber);
	
	Optional<DeliveryAgentModel> findByEmailAddress(String emailAddress);

	List<DeliveryAgentModel> findByActiveStatus(boolean status);
	
	Page<DeliveryAgentModel> findAllByOrderByDeliveryAgentIdDesc(Pageable pageable);



}
