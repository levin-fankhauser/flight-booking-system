package ch.fankhauser.levin.flightbookingsystem.booking;

import ch.fankhauser.levin.flightbookingsystem.flight.Flight;
import ch.fankhauser.levin.flightbookingsystem.passenger.Passenger;

import java.time.LocalDateTime;

public record BookingAdminRequestDTO(
		Passenger passenger,
		String origin,
		String destination,
		LocalDateTime departure,
		LocalDateTime arrival,
		Flight firstFlight,
		Flight secondFlight,
		Flight thirdFlight,
		LocalDateTime bookingDate,
		String createdBy
		) {

}
