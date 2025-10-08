package com.example.Porter.DeliveryRequest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Porter.Customer.CustomerModel;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequestModel, Long> {

	boolean existsByCustomerAndPickupAddressAndDropAddressAndParcelTypeAndParcelWeightAndScheduledPickUpTimeBetween(
			CustomerModel customer, String pickUpAddress, String dropAddress, String parceType, Double parcelWeight,
			LocalDateTime fromTime, LocalDateTime toTime);

	List<DeliveryRequestModel> findByIsActive(boolean status);
	
	Page<DeliveryRequestModel> findAllByOrderByDeliveryRequestIdDesc (Pageable pageable);

}
