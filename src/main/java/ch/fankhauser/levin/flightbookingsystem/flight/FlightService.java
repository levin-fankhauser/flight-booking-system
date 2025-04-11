package ch.fankhauser.levin.flightbookingsystem.flight;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> findAllFlights() {
		return flightRepository.findAll();
	}

	public Flight findFlightById(Long id) {
		return flightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));
	}

	public Flight createFlight(FlightRequestDTO flight) {
		return flightRepository.save(mapDtoToEntity(flight));
	}

	public Flight updateFlight(Long id, FlightRequestDTO flight) {
		return flightRepository.findById(id).map(existingFlight -> {
			existingFlight.setAirplane(flight.airplane());
			existingFlight.setOrigin(flight.origin());
			existingFlight.setDestination(flight.destination());
			existingFlight.setDeparture(flight.departure());
			existingFlight.setArrival(flight.arrival());
			return flightRepository.save(existingFlight);
		}).orElseGet(() -> createFlight(flight));
	}

	public void deleteFlight(Long id) {
		if (!flightRepository.existsById(id)) {
			throw new EntityNotFoundException("Flight not found with id " + id);
		}
		flightRepository.deleteById(id);
	}

	private Flight mapDtoToEntity(FlightRequestDTO flightRequestDTO) {
		Flight flight = new Flight();
		flight.setAirplane(flightRequestDTO.airplane());
		flight.setOrigin(flightRequestDTO.origin());
		flight.setDestination(flightRequestDTO.destination());
		flight.setDeparture(flightRequestDTO.departure());
		flight.setArrival(flightRequestDTO.arrival());
		flight.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return flight;
	}
}
