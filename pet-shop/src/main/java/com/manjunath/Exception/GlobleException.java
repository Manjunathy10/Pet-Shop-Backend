package com.manjunath.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobleException {

	@ExceptionHandler(SellerException.class	)
	public ResponseEntity<ErrorDetail> sellerExceptionHandler(SellerException se, WebRequest req) {

		ErrorDetail errorDetail=new ErrorDetail();
		
		errorDetail.setError(se.getMessage());
		errorDetail.setDetail(req.getDescription(false));
		errorDetail.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorDetail,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ProductException.class	)
	public ResponseEntity<ErrorDetail> productExceptionHandler(SellerException se, WebRequest req) {

		ErrorDetail errorDetail=new ErrorDetail();
		
		errorDetail.setError(se.getMessage());
		errorDetail.setDetail(req.getDescription(false));
		errorDetail.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<>(errorDetail,HttpStatus.BAD_REQUEST);
	}
}
