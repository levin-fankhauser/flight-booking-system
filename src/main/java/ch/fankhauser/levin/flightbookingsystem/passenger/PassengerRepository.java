package ch.fankhauser.levin.flightbookingsystem.passenger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
	List<Passenger> findAllByCreatedBy(String createdBy);
	Optional<Passenger> findByIdAndCreatedBy(Long id, String createdBy);
}
