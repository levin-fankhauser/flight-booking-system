package ch.fankhauser.levin.flightbookingsystem.flight;

import ch.fankhauser.levin.flightbookingsystem.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class FlightController {

	@GetMapping("/api/flight")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Flight>> getAllFlights() {
		// Service logic follows

		return ResponseEntity.ok(List.of());
	}

	@GetMapping("/api/flight/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
		// Service logic follows
		return ResponseEntity.ok(new Flight());
	}

	@PostMapping("/api/flight")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
		// Service logic follows
		return ResponseEntity.ok(new Flight());
	}

	@PutMapping("/api/flight/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flight) {
		// Service logic follows
		return ResponseEntity.ok(new Flight());
	}

	@DeleteMapping("/api/flight/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
		// Service logic follows
		return ResponseEntity.noContent().build();
	}
}
