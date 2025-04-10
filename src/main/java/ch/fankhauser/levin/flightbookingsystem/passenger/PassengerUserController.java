package ch.fankhauser.levin.flightbookingsystem.passenger;

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
@RequestMapping("/api/passenger")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class PassengerUserController {

	private final PassengerUserService passengerUserService;

	public PassengerUserController(PassengerUserService passengerUserService) {
		this.passengerUserService = passengerUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerUserService.getAllPassengers();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerUserService.getPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerUserService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
