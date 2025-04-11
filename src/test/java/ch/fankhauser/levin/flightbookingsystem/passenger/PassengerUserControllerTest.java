package ch.fankhauser.levin.flightbookingsystem.passenger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

class PassengerUserControllerTest {

	private PassengerUserService passengerUserService;
	private PassengerUserController passengerUserController;

	@BeforeEach
	void setUp() {
		passengerUserService = mock(PassengerUserService.class);
		passengerUserController = new PassengerUserController(passengerUserService);
	}

	@Test
	void getAllPassengers_returnsListOfPassengers() {
		Passenger passenger = new Passenger();
		when(passengerUserService.findAllPassengers()).thenReturn(List.of(passenger));

		ResponseEntity<List<Passenger>> response = passengerUserController.getAllPassengers();

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(1, response.getBody().size());
		verify(passengerUserService).findAllPassengers();
	}

	@Test
	void getPassengerById_returnsPassenger() {
		Passenger passenger = new Passenger();
		when(passengerUserService.findPassengerById(1L)).thenReturn(passenger);

		ResponseEntity<Passenger> response = passengerUserController.getPassengerById(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(passenger, response.getBody());
		verify(passengerUserService).findPassengerById(1L);
	}

	@Test
	void createPassenger_returnsCreatedPassenger() {
		PassengerRequestDTO request = new PassengerRequestDTO("Doe", "John", 21, "CH");
		Passenger created = new Passenger();
		when(passengerUserService.createPassenger(request)).thenReturn(created);

		ResponseEntity<Passenger> response = passengerUserController.createPassenger(request);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(created, response.getBody());
		verify(passengerUserService).createPassenger(request);
	}

	@Test
	void updatePassenger_returnsUpdatedPassenger() {
		PassengerRequestDTO updateRequest = new PassengerRequestDTO("Doe", "John", 21, "CH");
		Passenger updated = new Passenger();
		when(passengerUserService.updatePassenger(1L, updateRequest)).thenReturn(updated);

		ResponseEntity<Passenger> response = passengerUserController.updatePassenger(1L, updateRequest);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(updated, response.getBody());
		verify(passengerUserService).updatePassenger(1L, updateRequest);
	}

	@Test
	void deletePassenger_returnsNoContent() {
		doNothing().when(passengerUserService).deletePassenger(1L);

		ResponseEntity<Void> response = passengerUserController.deletePassenger(1L);

		assertEquals(204, response.getStatusCodeValue());
		assertNull(response.getBody());
		verify(passengerUserService).deletePassenger(1L);
	}
}
