package com.example.Porter.Location;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateModel,Long>{

	Optional<StateModel> findByStateNameOrStateCode(String stateName,String stateCode);
	
	Page<StateModel> findAllByOrderByStateIdDesc(Pageable pageable);
}
