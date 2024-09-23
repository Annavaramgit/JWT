package com.security.custom_exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnAuthorizedExceptionCls extends RuntimeException{

	private String errorMessage;
}
