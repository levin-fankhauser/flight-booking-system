package ch.fankhauser.levin.flightbookingsystem.passenger;

public record UserPassengerRequest(
		Long id,
		String lastname,
		String firstname,
		int age,
		String nationality
) {

}
