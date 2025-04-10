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
@Tag(name = "Airplane", description = "Verwaltung von Flugzeugen (Admin only)")
public class AirplaneController {

	private final AirplaneService airplaneService;

	public AirplaneController(AirplaneService airplaneService) {
		this.airplaneService = airplaneService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Alle Flugzeuge abrufen", description = "Gibt eine Liste aller Flugzeuge zurück.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Airplane>> getAllAirplanes() {
		List<Airplane> result = airplaneService.findAllAirplanes();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flugzeug nach ID abrufen", description = "Gibt ein einzelnes Flugzeug anhand der ID zurück.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Erfolgreich abgerufen"),
			@ApiResponse(responseCode = "404", description = "Flugzeug nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Airplane> getAirplaneById(
			@Parameter(description = "ID des Flugzeugs", required = true) @PathVariable Long id) {
		Airplane result = airplaneService.findAirplaneById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Neues Flugzeug erstellen", description = "Erstellt ein neues Flugzeug auf Basis der übermittelten Daten.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Flugzeug erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabe"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Airplane> createAirplane(
			@Parameter(description = "Daten des neuen Flugzeugs", required = true) @Valid @RequestBody AirplaneRequestDTO airplane) {
		Airplane savedAirplane = airplaneService.createAirplane(airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flugzeug aktualisieren", description = "Aktualisiert ein bestehendes Flugzeug anhand der ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Flugzeug erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabe"),
			@ApiResponse(responseCode = "404", description = "Flugzeug nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Airplane> updateAirplane(
			@Parameter(description = "ID des zu aktualisierenden Flugzeugs", required = true) @PathVariable Long id,
			@Parameter(description = "Aktualisierte Daten des Flugzeugs", required = true) @Valid @RequestBody AirplaneRequestDTO airplane) {
		Airplane savedAirplane = airplaneService.updateAirplane(id, airplane);
		return ResponseEntity.ok(savedAirplane);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Flugzeug löschen", description = "Löscht ein Flugzeug anhand der ID.")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Flugzeug erfolgreich gelöscht"),
			@ApiResponse(responseCode = "404", description = "Flugzeug nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Void> deleteAirplane(
			@Parameter(description = "ID des zu löschenden Flugzeugs", required = true) @PathVariable Long id) {
		airplaneService.deleteAirplane(id);
		return ResponseEntity.noContent().build();
	}
}
