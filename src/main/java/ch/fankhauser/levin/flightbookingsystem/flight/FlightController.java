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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Flight>> getAllFlights() {
		List<Flight> result = flightService.getAllFlights();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
		Flight result = flightService.getFlightById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> createFlight(@Valid @RequestBody Flight flight) {
		Flight savedFlight = flightService.createFlight(flight);
		return ResponseEntity.ok(savedFlight);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @Valid @RequestBody Flight flight) {
		Flight savedFlight = flightService.updateFlight(id, flight);
		return ResponseEntity.ok(savedFlight);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
		return ResponseEntity.noContent().build();
	}
}
