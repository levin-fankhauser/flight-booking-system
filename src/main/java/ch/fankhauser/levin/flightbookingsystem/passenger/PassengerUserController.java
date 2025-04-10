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
@Tag(name = "Passengers (User)", description = "Passagierverwaltung für reguläre Benutzer")
public class PassengerUserController {

	private final PassengerUserService passengerUserService;

	public PassengerUserController(PassengerUserService passengerUserService) {
		this.passengerUserService = passengerUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Alle eigenen Passagiere abrufen", description = "Gibt alle Passagiere zurück, die dem aktuell eingeloggten Benutzer zugeordnet sind.", responses = {
			@ApiResponse(responseCode = "200", description = "Passagiere erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerUserService.getAllPassengers();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Eigenen Passagier nach ID abrufen", description = "Gibt einen bestimmten Passagier zurück, sofern dieser dem aktuell eingeloggten Benutzer gehört.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerUserService.getPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Neuen Passagier erstellen", description = "Erstellt einen neuen Passagier, der dem aktuellen Benutzer zugeordnet wird.", responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Passenger> createPassenger(
			@Valid @RequestBody @Parameter(description = "Details des neuen Passagiers", required = true) PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Eigenen Passagier aktualisieren", description = "Aktualisiert einen eigenen Passagier anhand der ID.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Passenger> updatePassenger(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Aktualisierte Passagierdaten", required = true) PassengerRequestDTO passenger) {
		Passenger savedPassenger = passengerUserService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Eigenen Passagier löschen", description = "Löscht einen eigenen Passagier anhand seiner ID.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Passagier erfolgreich gelöscht"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerUserService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
