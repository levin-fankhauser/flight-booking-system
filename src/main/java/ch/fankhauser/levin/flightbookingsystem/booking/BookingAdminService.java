package ch.fankhauser.levin.flightbookingsystem.booking;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingAdminService {

	private final BookingRepository bookingRepository;

	public BookingAdminService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public List<Booking> findAllBookings() {
		return bookingRepository.findAll();
	}

	public Booking findBookingById(Long id) {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
	}

	public Booking createBooking(BookingAdminRequestDTO booking) {
				return bookingRepository.save(mapDtoToEntity(booking));
	}

	public Booking updateBooking(Long id, BookingAdminRequestDTO booking) {
		return bookingRepository.findById(id)
				.map(existingBooking -> {
					existingBooking.setPassenger(booking.passenger());
					existingBooking.setOrigin(booking.origin());
					existingBooking.setDestination(booking.destination());
					existingBooking.setDeparture(booking.departure());
					existingBooking.setArrival(booking.arrival());
					existingBooking.setFirstFlight(booking.firstFlight());
					existingBooking.setSecondFlight(booking.secondFlight());
					existingBooking.setThirdFlight(booking.thirdFlight());
					existingBooking.setBookingDate(booking.bookingDate());
					existingBooking.setCreatedBy(booking.createdBy());
					return bookingRepository.save(existingBooking);
				})
				.orElseGet(() -> bookingRepository.save(mapDtoToEntity(booking)));
	}

	public void deleteBooking(Long id) {
		if (!bookingRepository.existsById(id)) {
			throw new EntityNotFoundException("Booking not found with id " + id);
		}
		bookingRepository.deleteById(id);
	}

	private Booking mapDtoToEntity(BookingAdminRequestDTO booking) {
		Booking newBooking = new Booking();
		newBooking.setPassenger(booking.passenger());
		newBooking.setOrigin(booking.origin());
		newBooking.setDestination(booking.destination());
		newBooking.setDeparture(booking.departure());
		newBooking.setArrival(booking.arrival());
		newBooking.setFirstFlight(booking.firstFlight());
		newBooking.setSecondFlight(booking.secondFlight());
		newBooking.setThirdFlight(booking.thirdFlight());
		newBooking.setBookingDate(booking.bookingDate());
		newBooking.setCreatedBy(booking.createdBy());
		return newBooking;
	}
}
