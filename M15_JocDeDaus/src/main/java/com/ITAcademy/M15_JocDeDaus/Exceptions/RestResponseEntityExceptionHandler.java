package com.ITAcademy.M15_JocDeDaus.Exceptions;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//@ExceptionHandler(value = TypeMismatchException.class)
//public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex) {
//	return new ResponseEntity<Object>("typemismatch", HttpStatus.BAD_GATEWAY);
//}

//	@ExceptionHandler(value = NumberFormatException.class)
//	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
//		return new ResponseEntity<Object>("numform", HttpStatus.BAD_REQUEST);
//	}


//	@ExceptionHandler(value = NullPointerException.class)
//	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
//		Map<String, Object> body = new LinkedHashMap<>();
//		body.put("timestamp", LocalDateTime.now());
//		body.put("message", "City not found");
//		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(PlayerNotFoundException.class)
	public final ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
//				ex.getBindingResult().toString());
//		return new ResponseEntity<Object>("sdfsdf", HttpStatus.BAD_GATEWAY);
//	}

//	@Override
//	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//		Map<String, Object> body = new LinkedHashMap<>();
//		body.put("timestamp", LocalDate.now());
//		body.put("status", status.value());
//
//		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
//				.collect(Collectors.toList());
//
//		body.put("errors", errors);
//
//		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//	}

}
