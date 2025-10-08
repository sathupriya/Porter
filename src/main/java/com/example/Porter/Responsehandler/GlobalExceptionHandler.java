package com.example.Porter.Responsehandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Porter.Exception.AlreadyExistsException;
import com.example.Porter.Exception.FieldValidException;
import com.example.Porter.Exception.ResourceNotFoundException;


import com.example.Porter.Constants.APIResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	String userMessage=null;

	@ExceptionHandler(ResourceNotFoundException.class)
	public <T> ResponseEntity<APIResponseDto<T>> handleNotFound(ResourceNotFoundException ex){
		return GlobalExceptionHandler.buildError(userMessage,ex.getLocalizedMessage(),HttpStatus.NOT_FOUND,3);
	}
	
	@ExceptionHandler(FieldValidException.class)
	public <T> ResponseEntity<APIResponseDto<T>> handleValidation(FieldValidException ex){
		return GlobalExceptionHandler.buildError(userMessage,ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST,3);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public <T> ResponseEntity<APIResponseDto<T>> handleAlredyexists(AlreadyExistsException ex){
		return GlobalExceptionHandler.buildError(userMessage,ex.getLocalizedMessage(),HttpStatus.CONFLICT,3);
	}
	
	@ExceptionHandler(Exception.class)
	public  <T> ResponseEntity<APIResponseDto<T>> handleGenericException(Exception ex){
		return GlobalExceptionHandler.buildError(userMessage,ex.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR,3);
	}
//	
//	@ExceptionHandler(InvalidTokenException.class)
//	public static <T> ResponseEntity<APIResponseDto<T>> handleInvalidToken(InvalidTokenException ex){
//		return GlobalExceptionHandler.buildError(ex.getLocalizedMessage(),userMessage,HttpStatus.UNAUTHORIZED, 3);
//	}
	
	public  static <T> ResponseEntity<APIResponseDto<T>> buildError(String errorMes,String message, HttpStatus status,int flag){
		APIResponseDto<T>response = APIResponseDto.<T>builder().statusCode(status.value()).errorDescription(errorMes).flag(flag).responseMessage(message).data(null).build();
		return new ResponseEntity<>(response,status);
	}
	
	
}
