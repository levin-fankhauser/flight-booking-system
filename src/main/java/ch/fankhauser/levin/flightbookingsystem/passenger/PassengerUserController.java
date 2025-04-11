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
@RequestMapping("/api/passenger")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Passengers (User)", description = "Passenger management for regular users")
public class PassengerUserController {

	private final PassengerUserService passengerUserService;

	public PassengerUserController(PassengerUserService passengerUserService) {
		this.passengerUserService = passengerUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Retrieve all own passengers", description = "Returns all passengers associated with the currently logged-in user.", responses = {
			@ApiResponse(responseCode = "200", description = "Passengers successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerUserService.findAllPassengers();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Retrieve own passenger by ID", description = "Returns a specific passenger if they belong to the currently logged-in user.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully found"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found") })
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerUserService.findPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Create a new passenger", description = "Creates a new passenger associated with the currently logged-in user.", responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Passenger> createPassenger(
			@Valid @RequestBody @Parameter(description = "Details of the new passenger", required = true) PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Update own passenger", description = "Updates an own passenger based on the ID.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passenger successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found") })
	public ResponseEntity<Passenger> updatePassenger(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Updated passenger data", required = true) PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Delete own passenger", description = "Deletes an own passenger by their ID.", parameters = {
			@Parameter(name = "id", description = "ID of the passenger", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Passenger successfully deleted"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Passenger not found"),
			@ApiResponse(responseCode = "400", description = "Conflict: Cannot delete passenger with existing bookings") })
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerUserService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
