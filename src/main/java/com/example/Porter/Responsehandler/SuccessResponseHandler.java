package com.example.Porter.Responsehandler;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.example.Porter.Constants.APIResponseDto;

public class SuccessResponseHandler {

	public static <T> APIResponseDto<T> success(T data, String message) {
		return SuccessResponseHandler.buildSuccessWithData(message, HttpStatus.OK.value(), 1, data);
	}

	public static <T> APIResponseDto<Page<T>> success(Page<T> data, String message) {
		return SuccessResponseHandler.buildSuccessWithData(message, HttpStatus.OK.value(), 1, data);
	}

	public static <T> APIResponseDto<List<T>> success(List<T> data, String message) {
		return SuccessResponseHandler.buildSuccessWithData(message, HttpStatus.OK.value(), 1, data);
	}

	public static <T> APIResponseDto<T> success(String message) {
		return SuccessResponseHandler.buildSuccess(message, HttpStatus.OK.value(), 1);
	}

	public static <T> APIResponseDto<T> noDataFound(String message) {
		return SuccessResponseHandler.buildSuccess(message, HttpStatus.OK.value(), 1);
	}

	public static <T> APIResponseDto<T> buildSuccess(String message, int status, int flag) {
		return APIResponseDto.<T>builder().statusCode(status).flag(flag).responseMessage(message).data(null).build();

	}

	public static <T> APIResponseDto<T> buildSuccessWithData(String message, int status, int flag, T data) {
		return APIResponseDto.<T>builder().statusCode(status).flag(flag).responseMessage(message).data(data).build();

	}

}
