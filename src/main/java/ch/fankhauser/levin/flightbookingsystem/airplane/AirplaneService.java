package ch.fankhauser.levin.flightbookingsystem.airplane;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {

	private final AirplaneRepository airplaneRepository;

	public AirplaneService(AirplaneRepository airplaneRepository) {
		this.airplaneRepository = airplaneRepository;
	}

	public List<Airplane> findAllAirplanes() {
		return airplaneRepository.findAll();
	}

	public Airplane findAirplaneById(Long id) {
		return airplaneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Airplane not found with id: " + id));
	}

	public Airplane createAirplane(AirplaneRequestDTO airplane) {
		return airplaneRepository.save(mapDtoToEntity(airplane));
	}

	public Airplane updateAirplane(Long id, AirplaneRequestDTO airplane) {
		return airplaneRepository.findById(id).map(existingAirplane -> {
			existingAirplane.setBrand(airplane.brand());
			existingAirplane.setModel(airplane.model());
			existingAirplane.setConstructionYear(airplane.constructionYear());
			existingAirplane.setAirline(airplane.airline());
			existingAirplane.setSeatCapacity(airplane.seatCapacity());
			return airplaneRepository.save(existingAirplane);
		}).orElseGet(() -> createAirplane(airplane));
	}

	public void deleteAirplane(Long id) {
		if (!airplaneRepository.existsById(id)) {
			throw new EntityNotFoundException("Airplane not found with id " + id);
		}
		airplaneRepository.deleteById(id);
	}

	private Airplane mapDtoToEntity(AirplaneRequestDTO airplane) {
		Airplane entity = new Airplane();
		entity.setBrand(airplane.brand());
		entity.setModel(airplane.model());
		entity.setConstructionYear(airplane.constructionYear());
		entity.setAirline(airplane.airline());
		entity.setSeatCapacity(airplane.seatCapacity());
		entity.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return entity;
	}

}
