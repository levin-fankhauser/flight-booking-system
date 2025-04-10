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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airplane")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class AirplaneController {

	private final AirplaneService airplaneService;

	public AirplaneController(AirplaneService airplaneService) {
		this.airplaneService = airplaneService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Airplane>> getAllAirplanes() {
		List<Airplane> result = airplaneService.getAllAirplanes();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> getAirplaneById(@PathVariable Long id) {
		Airplane result = airplaneService.getAirplaneById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> createAirplane(@Valid @RequestBody Airplane airplane) {
		Airplane savedAirplane = airplaneService.createAirplane(airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Airplane> updateAirplane(@PathVariable Long id, @Valid @RequestBody Airplane airplane) {
		Airplane savedAirplane = airplaneService.updateAirplane(id, airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deleteAirplane(@PathVariable Long id) {
		airplaneService.deleteAirplane(id);
		return ResponseEntity.noContent().build();
	}
}
