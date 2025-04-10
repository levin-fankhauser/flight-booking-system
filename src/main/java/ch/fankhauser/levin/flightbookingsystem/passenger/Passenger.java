package ch.fankhauser.levin.flightbookingsystem.passenger;

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
public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String lastname;

	@Column(length = 100, nullable = false)
	@Size(max = 100)
	@NotEmpty
	private String firstname;

	@Column(nullable = false)
	@Min(0)
	@Max(999)
	private int age;

	@Column(length = 50, nullable = false)
	@Size(max = 50)
	@NotEmpty
	private String nationality;

	@Column(length = 255, nullable = false)
	@Size(max = 255)
	@NotEmpty
	private String createdBy;


}
