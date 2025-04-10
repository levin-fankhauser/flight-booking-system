package ch.fankhauser.levin.flightbookingsystem.passenger;

public record UserPassengerRequest(
		String lastname,
		String firstname,
		int age,
		String nationality
) {

}
