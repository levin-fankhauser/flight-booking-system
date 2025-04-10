package ch.fankhauser.levin.flightbookingsystem.booking;

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
@RequestMapping("/api/admin/booking")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Bookings (Admin)", description = "Administrative Verwaltung von Buchungen")
public class BookingAdminController {

	private final BookingAdminService bookingAdminService;

	public BookingAdminController(BookingAdminService bookingAdminService) {
		this.bookingAdminService = bookingAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Alle Buchungen abrufen", description = "Diese Operation gibt eine Liste aller Buchungen zurück.", responses = {
			@ApiResponse(responseCode = "200", description = "Buchungen erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingAdminService.findAllBookings();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Buchung nach ID abrufen", description = "Gibt eine einzelne Buchung anhand ihrer ID zurück.", parameters = {
			@Parameter(name = "id", description = "ID der Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich gefunden"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingAdminService.findBookingById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Neue Buchung erstellen", description = "Erstellt eine neue Buchung mit den angegebenen Daten.", responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Booking> createBooking(
			@Valid @RequestBody @Parameter(description = "Details der neuen Buchung", required = true) BookingAdminRequest booking) {
		Booking savedBooking = bookingAdminService.createBooking(booking);
		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Buchung aktualisieren", description = "Aktualisiert eine vorhandene Buchung anhand der ID.", parameters = {
			@Parameter(name = "id", description = "ID der zu aktualisierenden Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Booking> updateBooking(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Aktualisierte Buchungsdaten", required = true) BookingAdminRequest booking) {
		Booking updatedBooking = bookingAdminService.updateBooking(id, booking);
		return ResponseEntity.ok(updatedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Buchung löschen", description = "Löscht eine Buchung anhand ihrer ID.", parameters = {
			@Parameter(name = "id", description = "ID der zu löschenden Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Buchung erfolgreich gelöscht"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingAdminService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
