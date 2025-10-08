package com.example.Porter.Location;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationModel,Long>{

	Optional<LocationModel> findByPincode(String pinCode);
	
	Page<LocationModel> findAllByOrderByLocationIdDesc(Pageable pageable);
}
