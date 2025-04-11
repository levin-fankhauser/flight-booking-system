package ch.fankhauser.levin.flightbookingsystem.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findAllByCreatedBy(String createdBy);

	Optional<Booking> findByIdAndCreatedBy(Long id, String createdBy);
}
