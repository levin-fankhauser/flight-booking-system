package ch.fankhauser.levin.flightbookingsystem.booking;

import ch.fankhauser.levin.flightbookingsystem.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/booking")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class BookingAdminController {

	private final BookingAdminService bookingAdminService;

	public BookingAdminController(BookingAdminService bookingAdminService) {
		this.bookingAdminService = bookingAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingAdminService.findAllBookings();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingAdminService.findBookingById(id);

		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingAdminRequest booking) {
		Booking savedBooking = bookingAdminService.createBooking(booking);

		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingAdminRequest booking) {
		Booking updatedBooking = bookingAdminService.updateBooking(id, booking);

		return ResponseEntity.ok(updatedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingAdminService.deleteBooking(id);

		return ResponseEntity.noContent().build();
	}
}
