package ch.fankhauser.levin.flightbookingsystem.passenger;

public record PassengerAdminRequestDTO(
		String lastname,
		String firstname,
		int age,
		String nationality,
		String createdBy
) {

}
