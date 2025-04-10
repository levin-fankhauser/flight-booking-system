package ch.fankhauser.levin.flightbookingsystem.passenger;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerAdminService {

	private final PassengerRepository passengerRepository;

	public PassengerAdminService(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}

	public List<Passenger> findAllPassengers() {
		return passengerRepository.findAll();
	}

	public Passenger findPassengerById(Long id) {
		return passengerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Passenger not found with id: " + id));
	}

	public Passenger createPassenger(Passenger passenger) {
		return passengerRepository.save(passenger);
	}

	public Passenger updatePassenger(Long id, Passenger passenger) {
		return passengerRepository.findById(id)
				.map(existingPassenger -> {
					existingPassenger.setLastname(passenger.getLastname());
					existingPassenger.setFirstname(passenger.getFirstname());
					existingPassenger.setAge(passenger.getAge());
					existingPassenger.setNationality(passenger.getNationality());
					existingPassenger.setCreatedBy(passenger.getCreatedBy());
					return passengerRepository.save(existingPassenger);
				})
				.orElseGet(() -> passengerRepository.save(passenger));
	}

	public void deletePassenger(Long id) {
		if (!passengerRepository.existsById(id)) {
			throw new EntityNotFoundException("Airplane not found with id " + id);
		}
		passengerRepository.deleteById(id);	}
}
