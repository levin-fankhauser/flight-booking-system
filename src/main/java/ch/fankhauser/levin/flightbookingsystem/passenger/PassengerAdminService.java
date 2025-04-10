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
		return passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found with id: " + id));
	}

	public Passenger createPassenger(PassengerAdminRequestDTO passenger) {
		return passengerRepository.save(mapDtoToEntity(passenger));
	}

	public Passenger updatePassenger(Long id, PassengerAdminRequestDTO passenger) {
		return passengerRepository.findById(id).map(existingPassenger -> {
			existingPassenger.setLastname(passenger.lastname());
			existingPassenger.setFirstname(passenger.firstname());
			existingPassenger.setAge(passenger.age());
			existingPassenger.setNationality(passenger.nationality());
			existingPassenger.setCreatedBy(passenger.createdBy());
			return passengerRepository.save(existingPassenger);
		}).orElseGet(() -> createPassenger(passenger));
	}

	public void deletePassenger(Long id) {
		if (!passengerRepository.existsById(id)) {
			throw new EntityNotFoundException("Airplane not found with id " + id);
		}
		passengerRepository.deleteById(id);
	}

	private Passenger mapDtoToEntity(PassengerAdminRequestDTO passenger) {
		Passenger entity = new Passenger();
		entity.setLastname(passenger.lastname());
		entity.setFirstname(passenger.firstname());
		entity.setAge(passenger.age());
		entity.setNationality(passenger.nationality());
		entity.setCreatedBy(passenger.createdBy());
		return entity;
	}
}
