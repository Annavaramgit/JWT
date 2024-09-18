package com.security.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.security.custom_exceptions.BadRequestCls;
import com.security.custom_exceptions.UnAuthorizedExceptionCls;

@RestControllerAdvice
public class GlobalExceptionHandlingClass {
	
	@ExceptionHandler(UnAuthorizedExceptionCls.class)
	public ResponseEntity<String> handleUnAuthorizedExceptionCls(UnAuthorizedExceptionCls ex){
		
		return new ResponseEntity<>(ex.getErrorMessage(),HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadRequestCls.class)
	public ResponseEntity<String> handleBadRequestCls(BadRequestCls ex){
		return new ResponseEntity<>(ex.getErrorMessage(),HttpStatus.BAD_REQUEST);
		
	}

}
