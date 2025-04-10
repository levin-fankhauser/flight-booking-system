package ch.fankhauser.levin.flightbookingsystem.flight;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

	public FlightService(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getAllFlights() {
		return flightRepository.findAll();
	}

	public Flight getFlightById(Long id) {
		return flightRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + id));
	}

	public Flight createFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public Flight updateFlight(Long id, Flight flight) {
		return flightRepository.findById(id)
				.map(existingFlight -> {
					existingFlight.setAirplane(flight.getAirplane());
					existingFlight.setOrigin(flight.getOrigin());
					existingFlight.setDestination(flight.getDestination());
					existingFlight.setDeparture(flight.getDeparture());
					existingFlight.setArrival(flight.getArrival());
					existingFlight.setCreatedBy(flight.getCreatedBy());
					return flightRepository.save(existingFlight);
				})
				.orElseGet(() -> flightRepository.save(flight));
	}

	public void deleteFlight(Long id) {
		if (!flightRepository.existsById(id)) {
			throw new EntityNotFoundException("Flight not found with id " + id);
		}
		flightRepository.deleteById(id);
	}
}
