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
@RequestMapping("/api/booking")
@SecurityRequirement(name = "bearerAuth")
@Validated
@Tag(name = "Bookings (User)", description = "Buchungen aus Sicht eines Nutzers verwalten")
public class BookingUserController {

	private final BookingUserService bookingUserService;

	public BookingUserController(BookingUserService bookingUserService) {
		this.bookingUserService = bookingUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Alle Buchungen des aktuellen Nutzers abrufen", description = "Gibt alle Buchungen zurück, die dem angemeldeten Nutzer zugeordnet sind.", responses = {
			@ApiResponse(responseCode = "200", description = "Buchungen erfolgreich abgerufen"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingUserService.getAllBookings();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Buchung nach ID abrufen", description = "Gibt eine Buchung zurück, sofern sie dem aktuellen Nutzer gehört.", parameters = {
			@Parameter(name = "id", description = "ID der Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich gefunden"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden") })
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingUserService.getBookingById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Neue Buchung erstellen", description = "Erstellt eine neue Buchung für den angemeldeten Nutzer.", responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich erstellt"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert") })
	public ResponseEntity<Booking> createBooking(
			@Valid @RequestBody @Parameter(description = "Details der neuen Buchung", required = true) BookingRequest booking) {
		Booking savedBooking = bookingUserService.createBooking(booking);
		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Eigene Buchung aktualisieren", description = "Aktualisiert eine Buchung des angemeldeten Nutzers.", parameters = {
			@Parameter(name = "id", description = "ID der Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Buchung erfolgreich aktualisiert"),
			@ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden") })
	public ResponseEntity<Booking> updateBooking(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Aktualisierte Buchungsdaten", required = true) BookingRequest booking) {
		Booking savedBooking = bookingUserService.updateBooking(id, booking);
		return ResponseEntity.ok(savedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Eigene Buchung löschen", description = "Löscht eine Buchung, sofern sie dem angemeldeten Nutzer gehört.", parameters = {
			@Parameter(name = "id", description = "ID der zu löschenden Buchung", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Buchung erfolgreich gelöscht"),
			@ApiResponse(responseCode = "403", description = "Zugriff verweigert"),
			@ApiResponse(responseCode = "404", description = "Buchung nicht gefunden") })
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingUserService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
