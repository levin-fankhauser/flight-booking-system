package ch.fankhauser.levin.flightbookingsystem.airplane;

import ch.fankhauser.levin.flightbookingsystem.security.Roles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airplane")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Airplane", description = "Management of airplanes (Admin only)")
public class AirplaneController {

	private final AirplaneService airplaneService;

	public AirplaneController(AirplaneService airplaneService) {
		this.airplaneService = airplaneService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve all airplanes", description = "Returns a list of all airplanes.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Airplane>> getAllAirplanes() {
		List<Airplane> result = airplaneService.findAllAirplanes();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve airplane by ID", description = "Returns a single airplane by its ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "404", description = "Airplane not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Airplane> getAirplaneById(
			@Parameter(description = "ID of the airplane", required = true) @PathVariable Long id) {
		Airplane result = airplaneService.findAirplaneById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Create a new airplane", description = "Creates a new airplane based on the submitted data.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Airplane successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Airplane> createAirplane(
			@Parameter(description = "Data for the new airplane", required = true) @Valid @RequestBody AirplaneRequestDTO airplane) {
		Airplane savedAirplane = airplaneService.createAirplane(airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Update airplane", description = "Updates an existing airplane by ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Airplane successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input"),
			@ApiResponse(responseCode = "404", description = "Airplane not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Airplane> updateAirplane(
			@Parameter(description = "ID of the airplane to update", required = true) @PathVariable Long id,
			@Parameter(description = "Updated airplane data", required = true) @Valid @RequestBody AirplaneRequestDTO airplane) {
		Airplane savedAirplane = airplaneService.updateAirplane(id, airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Delete airplane", description = "Deletes an airplane by ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Airplane successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Airplane not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Void> deleteAirplane(
			@Parameter(description = "ID of the airplane to delete", required = true) @PathVariable Long id) {
		airplaneService.deleteAirplane(id);
		return ResponseEntity.noContent().build();
	}
}
