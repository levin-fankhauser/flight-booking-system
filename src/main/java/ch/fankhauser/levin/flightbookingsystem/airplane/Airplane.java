package ch.fankhauser.levin.flightbookingsystem.airplane;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Airplane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String brand;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String model;

	@Column(nullable = false)
	@Min(1900)
	@Max(2100)
	private int constructionYear;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String airline;

	@Column(nullable = false)
	@Min(1)
	@Max(1000)
	private int seatCapacity;

	@Column(length = 255, nullable = false, updatable = false)
	@Size(max = 255)
	@NotEmpty
	private String createdBy;
}
