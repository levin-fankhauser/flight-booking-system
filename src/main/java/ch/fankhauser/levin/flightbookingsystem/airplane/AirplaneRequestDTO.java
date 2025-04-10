package ch.fankhauser.levin.flightbookingsystem.airplane;

public record AirplaneRequestDTO(
		String brand,
		String model,
		int constructionYear,
		String airline,
		int seatCapacity
) {

}
