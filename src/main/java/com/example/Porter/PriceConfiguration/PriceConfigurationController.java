package com.example.Porter.PriceConfiguration;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Porter.Constants.APIResponseDto;
import com.example.Porter.Constants.PriceConfigurationDto;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/priceConfiguration")
@AllArgsConstructor
public class PriceConfigurationController {

	private final PriceConfigurationServiceImpl service;
	
	@PostMapping("/addPriceConfig")
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> addPrice(@RequestBody PriceConfigurationDto dto){
		return service.addPrice(dto);
	}

	@PatchMapping("/updateStatus/{id}/{status}")
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> updateStatus(@PathVariable Long id, @PathVariable boolean status){
		return service.updateStatus(id, status);
	}

	@GetMapping("/viewById/{id}")
	public ResponseEntity<APIResponseDto<PriceConfigurationDto>> viewById(@PathVariable Long id){
		return service.viewById(id);
	}
	
	@GetMapping("/viewByStatus/{status}")
	public ResponseEntity<APIResponseDto<List<PriceConfigurationDto>>> getByStatus(@PathVariable boolean status){
		return service.getByStatus(status);
	}

	@GetMapping("/getAll/{page}/{pageSize}")
    public ResponseEntity<APIResponseDto<Page<PriceConfigurationDto>>> getAllPrice(@PathVariable int page, @PathVariable int pageSize){
    	return service.getAllPrice(page, pageSize);
    }
	
	
}
