package ch.fankhauser.levin.flightbookingsystem.passenger;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class PassengerRepositoryTest {

	@Autowired
	private PassengerRepository passengerRepository;

	private Passenger createPassenger(String firstname, String lastname, int age, String nationality, String createdBy) {
		Passenger passenger = new Passenger();
		passenger.setFirstname(firstname);
		passenger.setLastname(lastname);
		passenger.setAge(age);
		passenger.setNationality(nationality);
		passenger.setCreatedBy(createdBy);
		return passenger;
	}

	@Test
	void testFindAllByCreatedBy() {
		Passenger p1 = createPassenger("Max", "Mustermann", 30, "CH", "user1");
		Passenger p2 = createPassenger("Erika", "Musterfrau", 25, "DE", "user1");
		Passenger p3 = createPassenger("John", "Doe", 40, "US", "user2");

		passengerRepository.saveAll(List.of(p1, p2, p3));

		List<Passenger> result = passengerRepository.findAllByCreatedBy("user1");

		assertThat(result).hasSize(2);
		assertThat(result).extracting(Passenger::getFirstname).containsExactlyInAnyOrder("Max", "Erika");
	}

	@Test
	void testFindByIdAndCreatedBy() {
		Passenger passenger = createPassenger("Max", "Mustermann", 30, "CH", "user1");
		Passenger saved = passengerRepository.save(passenger);

		Optional<Passenger> found = passengerRepository.findByIdAndCreatedBy(saved.getId(), "user1");
		Optional<Passenger> notFound = passengerRepository.findByIdAndCreatedBy(saved.getId(), "user2");

		assertThat(found).isPresent();
		assertThat(found.get().getFirstname()).isEqualTo("Max");
		assertThat(notFound).isEmpty();
	}
}
