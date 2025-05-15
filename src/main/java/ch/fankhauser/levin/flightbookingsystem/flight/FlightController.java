package ch.fankhauser.levin.flightbookingsystem.flight;

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
@RequestMapping("/api/flight")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Flights", description = "Management of flights (Admin only)")
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping
	@RolesAllowed({ Roles.Admin, Roles.User })
	@Operation(summary = "Retrieve all flights", description = "This operation returns a list of all available flights.", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Flight>> getAllFlights() {
		List<Flight> result = flightService.findAllFlights();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve flight by ID", description = "Returns the flight data for the specified flight.", parameters = {
			@Parameter(name = "id", description = "ID of the requested flight", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Flight successfully found"),
			@ApiResponse(responseCode = "404", description = "Flight not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
		Flight result = flightService.findFlightById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Create new flight", description = "Creates a new flight with the given data.", responses = {
			@ApiResponse(responseCode = "200", description = "Flight successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Flight> createFlight(
			@Valid @RequestBody @Parameter(description = "Flight data for the new flight", required = true) FlightRequestDTO flight) {
		Flight savedFlight = flightService.createFlight(flight);
		return ResponseEntity.ok(savedFlight);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Update flight", description = "Updates the data of an existing flight.", parameters = {
			@Parameter(name = "id", description = "ID of the flight to update", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Flight successfully updated"),
			@ApiResponse(responseCode = "404", description = "Flight not found"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Flight> updateFlight(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Updated flight data", required = true) FlightRequestDTO flight) {
		Flight savedFlight = flightService.updateFlight(id, flight);
		return ResponseEntity.ok(savedFlight);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Delete flight", description = "Deletes the flight with the specified ID.", parameters = {
			@Parameter(name = "id", description = "ID of the flight to delete", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Flight successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Flight not found"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "409", description = "Conflict: Cannot delete flight with existing bookings") })
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
		return ResponseEntity.noContent().build();
	}
}
