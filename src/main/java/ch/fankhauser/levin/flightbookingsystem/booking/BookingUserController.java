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
@RequestMapping("/api/booking")
@SecurityRequirement(name = "bearerAuth")
@Validated
public class BookingUserController {

	private final BookingUserService bookingUserService;

	public BookingUserController(BookingUserService bookingUserService) {
		this.bookingUserService = bookingUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingUserService.getAllBookings();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingUserService.getBookingById(id);

		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest booking) {
		Booking savedBooking = bookingUserService.createBooking(booking);

		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequest booking) {
		Booking savedBooking = bookingUserService.updateBooking(id, booking);

		return ResponseEntity.ok(savedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingUserService.deleteBooking(id);

		return ResponseEntity.noContent().build();
	}
}
