package ch.fankhauser.levin.flightbookingsystem.flight;

import ch.fankhauser.levin.flightbookingsystem.airplane.Airplane;

import java.time.LocalDateTime;

public record FlightRequestDTO(
		Airplane airplane,
		String origin,
		String destination,
		LocalDateTime departure,
		LocalDateTime arrival) {

}
