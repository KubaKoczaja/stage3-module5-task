package com.mjc.school.controller;

import com.mjc.school.service.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

		@ExceptionHandler(BindException.class)
		@ResponseBody
		public ResponseEntity<List<ObjectError>>	bindErrorHandler(HttpServletRequest req, BindException e) {
				List<ObjectError> allErrors = e.getAllErrors();
				return new ResponseEntity<>(allErrors, HttpStatus.BAD_REQUEST);
		}

		@ExceptionHandler(CustomException.class)
		@ResponseBody
		public ResponseEntity<String>	customErrorHandler(HttpServletRequest req, CustomException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
}
