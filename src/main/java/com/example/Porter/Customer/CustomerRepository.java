package com.example.Porter.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel,Long>{

	Optional<CustomerModel> findByEmailAddress(String emailAddress);
	
	Optional<CustomerModel> findByMobileNumber(String mobileNumber);
	
	Page<CustomerModel> findAllByOrderByCustomerIdDesc(Pageable pageable);
	
	List<CustomerModel> findByIsActive(boolean status);
}
