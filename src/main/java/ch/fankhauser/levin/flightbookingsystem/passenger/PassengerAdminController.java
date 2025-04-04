package ch.fankhauser.levin.flightbookingsystem.passenger;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Validated
public class PassengerAdminController {

	@GetMapping("/api/admin/passenger")
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		// Service logic folgt
		Passenger passenger = new Passenger();
		passenger.setId(1L);
		passenger.setFirstname("John");
		passenger.setLastname("Doe");
		passenger.setAge(25);
		passenger.setNationality("Swiss");
		passenger.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

		return ResponseEntity.ok(List.of(passenger, passenger));
	}

	@GetMapping("/api/admin/passenger/{id}")
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		// Service logic folgt
		return ResponseEntity.ok(new Passenger());
	}

	@PostMapping("/api/admin/passenger")
	public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody Passenger passenger) {
		// Service logic folgt
		return ResponseEntity.ok(new Passenger());
	}

	@PutMapping("/api/admin/passenger/{id}")
	public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody Passenger passenger) {
		// Service logic folgt
		return ResponseEntity.ok(new Passenger());
	}

	@DeleteMapping("/api/admin/passenger/{id}")
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		// Service logic folgt
		return ResponseEntity.noContent().build();
	}
}
