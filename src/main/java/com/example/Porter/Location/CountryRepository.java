package com.example.Porter.Location;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryModel,Long>{

	Optional<CountryModel> findByCountryCodeOrCountryName(String countryCode,String countryName);
	
	Page<CountryModel> findAllByOrderByCountryIdDesc(Pageable pageable);
	
	Optional<CountryModel> findByCountryIdAndActiveStatus(Long countryId, boolean activeStatus);
}
