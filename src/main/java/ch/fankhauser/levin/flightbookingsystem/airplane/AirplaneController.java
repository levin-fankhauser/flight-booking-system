package ch.fankhauser.levin.flightbookingsystem.airplane;

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
public class AirplaneController {

	@GetMapping("/api/airplane")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Airplane>> getAirplanes() {
		// Service logic follows
		return ResponseEntity.ok(List.of());
	}

	@GetMapping("/api/airplane/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> getAirplaneById(@PathVariable Long id) {
		// Service logic follows
		return ResponseEntity.ok(new Airplane());
	}

	@PostMapping("/api/airplane")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> createAirplane(@Valid @RequestBody Airplane airplane) {
		// Service logic follows
		return ResponseEntity.ok(new Airplane());
	}

	@PutMapping("/api/airplane/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> updateAirplane(@PathVariable Long id, @Valid @RequestBody Airplane airplane) {
		// Service logic follows
		return ResponseEntity.ok(new Airplane());
	}

	@DeleteMapping("/api/airplane/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
		// Service logic follows
		return ResponseEntity.noContent().build();
	}
}
