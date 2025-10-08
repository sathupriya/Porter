package com.example.Porter.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoginHistoryRepository extends JpaRepository<CustomerLoginHistoryModel, Long> {
	
	Optional<CustomerLoginHistoryModel> findTopByCustomerCustomerIdOrderByCustomerLoginIdDesc(Long customerId);

}
