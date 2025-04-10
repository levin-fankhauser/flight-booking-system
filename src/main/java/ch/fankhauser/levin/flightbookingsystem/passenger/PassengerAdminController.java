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
@RequestMapping("/api/admin/passenger")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class PassengerAdminController {

	private final PassengerAdminService passengerAdminService;

	public PassengerAdminController(PassengerAdminService passengerAdminService) {
		this.passengerAdminService = passengerAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerAdminService.findAllPassengers();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerAdminService.findPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Passenger> createPassenger(@Valid @RequestBody Passenger passenger) {
		Passenger savedPassenger = passengerAdminService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @Valid @RequestBody Passenger passenger) {
		Passenger savedPassenger = passengerAdminService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerAdminService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
