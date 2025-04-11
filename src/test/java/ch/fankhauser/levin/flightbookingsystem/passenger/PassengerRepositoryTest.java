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

	@Test
	void testSavePassenger() {
		Passenger passenger = createPassenger("Lena", "Test", 28, "AT", "user3");

		Passenger saved = passengerRepository.save(passenger);

		assertThat(saved.getId()).isNotNull();
		assertThat(passengerRepository.findById(saved.getId())).isPresent();
	}

	@Test
	void testDeletePassenger() {
		Passenger passenger = createPassenger("Tom", "Tester", 35, "IT", "user4");
		Passenger saved = passengerRepository.save(passenger);

		assertThat(passengerRepository.findById(saved.getId())).isPresent();

		passengerRepository.delete(saved);

		assertThat(passengerRepository.findById(saved.getId())).isEmpty();
	}

	@Test
	void testFindAll() {
		Passenger p1 = createPassenger("Anna", "Admin", 22, "CH", "admin");
		Passenger p2 = createPassenger("Bob", "Builder", 33, "DE", "admin");

		passengerRepository.saveAll(List.of(p1, p2));

		List<Passenger> allPassengers = passengerRepository.findAll();

		assertThat(allPassengers).hasSize(2);
		assertThat(allPassengers).extracting(Passenger::getFirstname).containsExactlyInAnyOrder("Anna", "Bob");
	}

	@Test
	void testFindById() {
		Passenger passenger = createPassenger("Lisa", "Lang", 29, "AT", "admin");
		Passenger saved = passengerRepository.save(passenger);

		Optional<Passenger> found = passengerRepository.findById(saved.getId());
		Optional<Passenger> notFound = passengerRepository.findById(saved.getId() + 1);

		assertThat(found).isPresent();
		assertThat(found.get().getFirstname()).isEqualTo("Lisa");
		assertThat(notFound).isEmpty();
	}

	@Test
	void testExistsById() {
		Passenger passenger = createPassenger("Karl", "Kurz", 31, "IT", "admin");
		Passenger saved = passengerRepository.save(passenger);

		boolean exists = passengerRepository.existsById(saved.getId());
		boolean doesNotExist = passengerRepository.existsById(saved.getId() + 1);

		assertThat(exists).isTrue();
		assertThat(doesNotExist).isFalse();
	}

	@Test
	void testDeleteById() {
		Passenger passenger = createPassenger("Susi", "Schmidt", 27, "FR", "admin");
		Passenger saved = passengerRepository.save(passenger);
		Long id = saved.getId();

		assertThat(passengerRepository.findById(id)).isPresent();

		passengerRepository.deleteById(id);

		assertThat(passengerRepository.findById(id)).isEmpty();
	}

}
