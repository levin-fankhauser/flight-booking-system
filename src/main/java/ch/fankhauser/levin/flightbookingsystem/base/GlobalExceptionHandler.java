package ch.fankhauser.levin.flightbookingsystem.base;

import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

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
