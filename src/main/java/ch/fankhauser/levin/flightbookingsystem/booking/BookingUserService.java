package ch.fankhauser.levin.flightbookingsystem.booking;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingUserService {

	private final BookingRepository bookingRepository;

	public BookingUserService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public List<Booking> findAllBookings() {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return bookingRepository.findAllByCreatedBy(uname);
	}

	public Booking findBookingById(Long id) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return bookingRepository.findByIdAndCreatedBy(id, uname)
				.orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id + " and createdBy: " + uname));
	}

	public Booking createBooking(BookingRequestDTO booking) {
		Booking newBooking = mapDtoToEntity(booking);
		newBooking.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
		return bookingRepository.save(newBooking);
	}

	public Booking updateBooking(Long id, BookingRequestDTO booking) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return bookingRepository.findByIdAndCreatedBy(id, uname).map(existingBooking -> {
			existingBooking.setPassenger(booking.passenger());
			existingBooking.setOrigin(booking.origin());
			existingBooking.setDestination(booking.destination());
			existingBooking.setDeparture(booking.departure());
			existingBooking.setArrival(booking.arrival());
			existingBooking.setFirstFlight(booking.firstFlight());
			existingBooking.setSecondFlight(booking.secondFlight());
			existingBooking.setThirdFlight(booking.thirdFlight());
			existingBooking.setBookingDate(booking.bookingDate());
			return bookingRepository.save(existingBooking);
		}).orElseGet(() -> createBooking(booking));
	}

	public void deleteBooking(Long id) {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		bookingRepository.findByIdAndCreatedBy(id, uname).ifPresentOrElse(
				bookingRepository::delete, () -> {
					throw new EntityNotFoundException("Booking not found with id: " + id + " and createdBy: " + uname);
				});
	}

	private Booking mapDtoToEntity(BookingRequestDTO booking) {
		Booking entity = new Booking();
		entity.setPassenger(booking.passenger());
		entity.setOrigin(booking.origin());
		entity.setDestination(booking.destination());
		entity.setDeparture(booking.departure());
		entity.setArrival(booking.arrival());
		entity.setFirstFlight(booking.firstFlight());
		entity.setSecondFlight(booking.secondFlight());
		entity.setThirdFlight(booking.thirdFlight());
		entity.setBookingDate(booking.bookingDate());
		return entity;
	}
}
