package com.example.Porter.Constants;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceConfigurationDto {

	private Long priceConfigId;
	private Double baseFare;
	private Double ratePerKm;
	private Double ratePerKg;
	private Double cgstPer;
	private Double sgstPer;
	private Double igstPer;
	private boolean isTaxIncluded;
	private String zoneType;
	private boolean isActive;
	private String createdBy;
	private String modifiedBy;
	
	
}
