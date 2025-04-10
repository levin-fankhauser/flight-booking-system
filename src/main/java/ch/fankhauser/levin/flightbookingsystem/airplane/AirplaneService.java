package ch.fankhauser.levin.flightbookingsystem.airplane;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;

	public AirplaneService(AirplaneRepository airplaneRepository) {
		this.airplaneRepository = airplaneRepository;
	}

	public List<Airplane> getAllAirplanes() {
		return airplaneRepository.findAll();
	}

	public Airplane getAirplaneById(Long id) {
		return airplaneRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Airplane not found with id: " + id));
	}

	public Airplane createAirplane(Airplane airplane) {
		return airplaneRepository.save(airplane);
	}

	public Airplane updateAirplane(Long id, Airplane airplane) {
		return airplaneRepository.findById(id)
				.map(existingAirplane -> {
					existingAirplane.setBrand(airplane.getBrand());
					existingAirplane.setModel(airplane.getModel());
					existingAirplane.setConstructionYear(airplane.getConstructionYear());
					existingAirplane.setAirline(airplane.getAirline());
					existingAirplane.setSeatCapacity(airplane.getSeatCapacity());
					return airplaneRepository.save(existingAirplane);
				})
				.orElseGet(() -> airplaneRepository.save(airplane));
	}

	public void deleteAirplane(Long id) {
		if (!airplaneRepository.existsById(id)) {
			throw new EntityNotFoundException("Airplane not found with id " + id);
		}
		airplaneRepository.deleteById(id);
	}

}
