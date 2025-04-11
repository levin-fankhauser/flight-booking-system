package ch.fankhauser.levin.flightbookingsystem.passenger;

import ch.fankhauser.levin.flightbookingsystem.security.Roles;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/passenger")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Passengers (Admin)", description = "Passenger management for admins")
public class PassengerAdminController {

	private final PassengerAdminService passengerAdminService;

	public PassengerAdminController(PassengerAdminService passengerAdminService) {
		this.passengerAdminService = passengerAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve all passengers", description = "Returns a list of all registered passengers.", responses = {
			@ApiResponse(responseCode = "200", description = "Passengers successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerAdminService.findAllPassengers();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve passenger by ID", description = "Returns a passenger based on the provided ID.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully found"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found") })
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerAdminService.findPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Create new passenger", description = "Creates a new passenger with the provided data.", responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Passenger> createPassenger(
			@Valid @RequestBody @Parameter(description = "Details of the new passenger", required = true) PassengerAdminRequestDTO passenger) {
		Passenger savedPassenger = passengerAdminService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Update passenger", description = "Updates an existing passenger based on the provided ID.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found") })
	public ResponseEntity<Passenger> updatePassenger(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Updated passenger data", required = true) PassengerAdminRequestDTO passenger) {
		Passenger savedPassenger = passengerAdminService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Delete passenger", description = "Deletes a passenger based on the provided ID.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Passenger successfully deleted"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found"),
			@ApiResponse(responseCode = "409", description = "Conflict: Cannot delete passenger with existing bookings") })
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerAdminService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
