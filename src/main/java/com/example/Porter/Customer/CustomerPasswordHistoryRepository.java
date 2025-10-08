package com.example.Porter.Customer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPasswordHistoryRepository extends JpaRepository<CustomerPasswordHistoryModel,Long>{

	CustomerPasswordHistoryModel findByCustomerCustomerIdAndCurrentPasswordStatus(Long customerId,boolean status);
	
	Optional<CustomerPasswordHistoryModel> findByCustomerCustomerId(Long customerId);
}
