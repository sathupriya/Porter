package com.example.Porter.PriceConfiguration;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceConfigurationRepository extends JpaRepository<PriceConfigurationModel,Long> {

	List<PriceConfigurationModel> findByIsActiveTrue();
	
	List<PriceConfigurationModel> findByIsActive(boolean status);
	
	Page<PriceConfigurationModel> findAllByOrderByPriceConfigIdDesc(Pageable pageable);
	
	boolean existsByBaseFareAndRatePerKgAndRatePerKm(Double baseFare, Double ratePerKg, Double ratePerKm);
}
