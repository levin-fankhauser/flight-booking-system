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
@Tag(name = "Flights", description = "Verwaltung von Flügen (Admin only)")
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Alle Flüge abrufen", description = "Diese Operation gibt eine Liste aller verfügbaren Flüge zurück.", responses = {
			@ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Flight>> getAllFlights() {
		List<Flight> result = flightService.findAllFlights();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flug nach ID abrufen", description = "Gibt die Flugdaten zum angegebenen Flug zurück.", parameters = {
			@Parameter(name = "id", description = "ID des gesuchten Flugs", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Flug erfolgreich gefunden"),
			@ApiResponse(responseCode = "404", description = "Flug nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
		Flight result = flightService.findFlightById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Neuen Flug erstellen", description = "Erstellt einen neuen Flug mit den angegebenen Daten.", responses = {
			@ApiResponse(responseCode = "200", description = "Flug erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Flight> createFlight(
			@Valid @RequestBody @Parameter(description = "Flugdaten für den neuen Flug", required = true) FlightRequestDTO flight) {
		Flight savedFlight = flightService.createFlight(flight);
		return ResponseEntity.ok(savedFlight);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flug aktualisieren", description = "Aktualisiert die Daten eines bestehenden Flugs.", parameters = {
			@Parameter(name = "id", description = "ID des zu aktualisierenden Flugs", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Flug erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "404", description = "Flug nicht gefunden"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Flight> updateFlight(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Aktualisierte Flugdaten", required = true) FlightRequestDTO flight) {
		Flight savedFlight = flightService.updateFlight(id, flight);
		return ResponseEntity.ok(savedFlight);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flug löschen", description = "Löscht den Flug mit der angegebenen ID.", parameters = {
			@Parameter(name = "id", description = "ID des zu löschenden Flugs", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Flug erfolgreich gelöscht"),
			@ApiResponse(responseCode = "404", description = "Flug nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
		flightService.deleteFlight(id);
		return ResponseEntity.noContent().build();
	}
}
