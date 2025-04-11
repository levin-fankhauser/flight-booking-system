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
@Tag(name = "Bookings (Admin)", description = "Administrative management of bookings")
public class BookingAdminController {

	private final BookingAdminService bookingAdminService;

	public BookingAdminController(BookingAdminService bookingAdminService) {
		this.bookingAdminService = bookingAdminService;
	}

	@GetMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve all bookings", description = "This operation returns a list of all bookings.", responses = {
			@ApiResponse(responseCode = "200", description = "Bookings successfully retrieved"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<Booking> result = bookingAdminService.findAllBookings();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Retrieve booking by ID", description = "Returns a single booking by its ID.", parameters = {
			@Parameter(name = "id", description = "ID of the booking", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully found"),
			@ApiResponse(responseCode = "404", description = "Booking not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking result = bookingAdminService.findBookingById(id);
		return ResponseEntity.ok(result);
	}

	@PostMapping
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Create new booking", description = "Creates a new booking with the provided data.", responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully created"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Booking> createBooking(
			@Valid @RequestBody @Parameter(description = "Details of the new booking", required = true) BookingAdminRequestDTO booking) {
		Booking savedBooking = bookingAdminService.createBooking(booking);
		return ResponseEntity.ok(savedBooking);
	}

	@PutMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Update booking", description = "Updates an existing booking by its ID.", parameters = {
			@Parameter(name = "id", description = "ID of the booking to update", required = true) }, responses = {
			@ApiResponse(responseCode = "200", description = "Booking successfully updated"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Booking not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Booking> updateBooking(
			@PathVariable Long id,
			@Valid @RequestBody @Parameter(description = "Updated booking data", required = true) BookingAdminRequestDTO booking) {
		Booking updatedBooking = bookingAdminService.updateBooking(id, booking);
		return ResponseEntity.ok(updatedBooking);
	}

	@DeleteMapping("/{id}")
	@RolesAllowed(Roles.Admin)
	@Operation(summary = "Delete booking", description = "Deletes a booking by its ID.", parameters = {
			@Parameter(name = "id", description = "ID of the booking to delete", required = true) }, responses = {
			@ApiResponse(responseCode = "204", description = "Booking successfully deleted"),
			@ApiResponse(responseCode = "404", description = "Booking not found"),
			@ApiResponse(responseCode = "403", description = "Access denied") })
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingAdminService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
