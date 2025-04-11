package ch.fankhauser.levin.flightbookingsystem.passenger;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerUserService {

	private final PassengerRepository passengerRepository;

	public PassengerUserService(PassengerRepository passengerRepository) {
		this.passengerRepository = passengerRepository;
	}

	public List<Passenger> getAllPassengers() {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return passengerRepository.findAllByCreatedBy(uname);
	}

	public Passenger getPassengerById(Long id) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return passengerRepository.findByIdAndCreatedBy(id, uname)
				.orElseThrow(() -> new EntityNotFoundException("Passenger not found with id: " + id + " and createdBy: " + uname));
	}

	public Passenger createPassenger(PassengerRequestDTO passenger) {
		return passengerRepository.save(mapDtoToEntity(passenger));
	}

	public Passenger updatePassenger(Long id, PassengerRequestDTO passenger) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return passengerRepository.findByIdAndCreatedBy(id, uname).map(existingPassenger -> {
			existingPassenger.setLastname(passenger.lastname());
			existingPassenger.setFirstname(passenger.firstname());
			existingPassenger.setAge(passenger.age());
			existingPassenger.setNationality(passenger.nationality());
			return passengerRepository.save(existingPassenger);
		}).orElseGet(() -> createPassenger(passenger));
	}

	public void deletePassenger(Long id) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		passengerRepository.findByIdAndCreatedBy(id, uname).ifPresentOrElse(
				passengerRepository::delete, () -> {
					throw new EntityNotFoundException("Passenger not found with id: " + id + " and createdBy: " + uname);
				});
	}

	private Passenger mapDtoToEntity(PassengerRequestDTO passenger) {
		Passenger entity = new Passenger();
		entity.setLastname(passenger.lastname());
		entity.setFirstname(passenger.firstname());
		entity.setAge(passenger.age());
		entity.setNationality(passenger.nationality());
		entity.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return entity;
	}
}
