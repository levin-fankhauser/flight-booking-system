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

	public List<Booking> getAllBookings() {
		String uname = SecurityContextHolder.getContext().getAuthentication().getName();
		return bookingRepository.findAllByCreatedBy(uname);
	}

	public Booking getBookingById(Long id) {
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
		return newBooking;
	}
}
