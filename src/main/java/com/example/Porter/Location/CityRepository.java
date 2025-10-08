package com.example.Porter.Location;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityModel,Long>{

	Optional<CityModel> findByCityCodeOrCityName(String cityCode, String cityName);
	
	Page<CityModel> findAllByOrderByCityIdDesc(Pageable pageable);
}
