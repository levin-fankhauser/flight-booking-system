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
@Tag(name = "Passengers (Admin)", description = "Passagierverwaltung für Admins")
public class PassengerAdminController {

	private final PassengerAdminService passengerAdminService;

	public PassengerAdminController(PassengerAdminService passengerAdminService) {
		this.passengerAdminService = passengerAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Alle Passagiere abrufen", description = "Gibt eine Liste aller registrierten Passagiere zurück.", responses = {
			@ApiResponse(responseCode = "200", description = "Passagiere erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Passenger>> getAllPassengers() {
		List<Passenger> result = passengerAdminService.findAllPassengers();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Passagier nach ID abrufen", description = "Gibt einen Passagier anhand seiner ID zurück.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
		Passenger result = passengerAdminService.findPassengerById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Neuen Passagier erstellen", description = "Erstellt einen neuen Passagier mit den übergebenen Daten.", responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Passenger> createPassenger(
			@Valid @RequestBody @Parameter(description = "Details des neuen Passagiers", required = true) PassengerAdminRequestDTO passenger) {
		Passenger savedPassenger = passengerAdminService.createPassenger(passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Passagier aktualisieren", description = "Aktualisiert einen bestehenden Passagier anhand seiner ID.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Passagier erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Passenger> updatePassenger(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Aktualisierte Passagierdaten", required = true) PassengerAdminRequestDTO passenger) {
		Passenger savedPassenger = passengerAdminService.updatePassenger(id, passenger);
		return ResponseEntity.ok(savedPassenger);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Passagier löschen", description = "Löscht einen Passagier anhand seiner ID.", parameters = {
			@Parameter(name = "id", description = "ID des Passagiers", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Passagier erfolgreich gelöscht"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Passagier nicht gefunden") })
	public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
		passengerAdminService.deletePassenger(id);
		return ResponseEntity.noContent().build();
	}
}
