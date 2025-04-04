package ch.fankhauser.levin.flightbookingsystem.flight;

import ch.fankhauser.levin.flightbookingsystem.airplane.Airplane;
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
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "airplane_id", nullable = false)
	private Airplane airplane;

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

	@Column(length = 255, nullable = false, updatable = false)
	@Size(max = 255)
	@NotEmpty
	private String createdBy;
}