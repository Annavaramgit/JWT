package com.security.custom_exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestCls extends RuntimeException{
	
	private String errorMessage;

}
