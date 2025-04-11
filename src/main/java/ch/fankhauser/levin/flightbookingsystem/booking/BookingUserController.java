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
@Tag(name = "Bookings (User)", description = "Manage bookings from the perspective of a user")
public class BookingUserController {

	private final BookingUserService bookingUserService;

	public BookingUserController(BookingUserService bookingUserService) {
		this.bookingUserService = bookingUserService;
	}

	@GetMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Retrieve all bookings of the current user", description = "Returns all bookings associated with the authenticated user.", responses = {
			@ApiResponse(responseCode = "200", description = "Bookings successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingUserService.getAllBookings();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Retrieve booking by ID", description = "Returns a booking if it belongs to the authenticated user.", parameters = {
			@Parameter(name = "id", description = "ID of the booking", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully found"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Booking not found") })
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingUserService.getBookingById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.User)
	@Operation(summary = "Create a new booking", description = "Creates a new booking for the authenticated user.", responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Booking> createBooking(
			@Valid @RequestBody @Parameter(description = "Details of the new booking", required = true) BookingRequestDTO booking) {
		Booking savedBooking = bookingUserService.createBooking(booking);
		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Update own booking", description = "Updates a booking of the authenticated user.", parameters = {
			@Parameter(name = "id", description = "ID of the booking", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Booking not found") })
	public ResponseEntity<Booking> updateBooking(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Updated booking data", required = true) BookingRequestDTO booking) {
		Booking savedBooking = bookingUserService.updateBooking(id, booking);
		return ResponseEntity.ok(savedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.User)
	@Operation(summary = "Delete own booking", description = "Deletes a booking if it belongs to the authenticated user.", parameters = {
			@Parameter(name = "id", description = "ID of the booking to delete", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Booking successfully deleted"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "404", description = "Booking not found") })
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingUserService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
