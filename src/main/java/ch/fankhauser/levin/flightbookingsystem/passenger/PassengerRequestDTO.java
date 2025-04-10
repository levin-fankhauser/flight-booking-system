package ch.fankhauser.levin.flightbookingsystem.passenger;

public record PassengerRequestDTO(
		String lastname,
		String firstname,
		int age,
		String nationality
) {

}
