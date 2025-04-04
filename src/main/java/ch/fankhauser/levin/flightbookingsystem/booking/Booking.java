package ch.fankhauser.levin.flightbookingsystem.booking;

import ch.fankhauser.levin.flightbookingsystem.flight.Flight;
import ch.fankhauser.levin.flightbookingsystem.passenger.Passenger;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "passenger_id", nullable = false)
	private Passenger passenger;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String origin;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String destination;

	@Column(nullable = false)
	private LocalDateTime departure;

	@Column(nullable = false)
	private LocalDateTime arrival;

	@ManyToOne
	@JoinColumn(name = "first_flight_id", nullable = false)
	private Flight firstFlight;

	@ManyToOne
	@JoinColumn(name = "second_flight_id")
	private Flight secondFlight;

	@ManyToOne
	@JoinColumn(name = "third_flight_id")
	private Flight thirdFlight;

	@Column(nullable = false)
	private LocalDateTime bookingDate;

	@Column(length = 255, nullable = false, updatable = false)
	@Size(max = 255)
	@NotEmpty
	private String createdBy;
}