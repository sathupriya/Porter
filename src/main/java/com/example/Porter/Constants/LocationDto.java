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
public class LocationDto {

	private Long countryId;
	private String countryName;
	private String countryCode;
	private Long stateId;
	private String stateName;
	private String stateCode;
	private Long cityId;
	private String cityName;
	private String cityCode;
	private Long locationId;
	private String landMark;
	private String pincode;
	private String createdBy;
	private String modifiedBy;
	
	
}
