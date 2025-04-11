package ch.fankhauser.levin.flightbookingsystem.base;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		StringBuilder errorMessage = new StringBuilder("Validation failed for the following fields: ");

		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
		}

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(errorMessage.toString());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations().forEach(violation -> {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({EntityNotFoundException.class})
	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException exception) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exception.getMessage());
	}

	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body("Delete referenced Object first to delete this Object -> Data integrity violation: " + exception.getMessage());
	}
}
