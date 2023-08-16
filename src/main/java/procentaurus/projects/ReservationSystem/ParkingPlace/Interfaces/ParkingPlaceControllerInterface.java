package procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.ParkingPlace.Dtos.ParkingPlaceUpdateDto;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ParkingPlaceControllerInterface {

    ResponseEntity<?> findSingleParkingPlace(int number);

    ResponseEntity<List<ParkingPlace>> findParkingPlaces(Map<String, String> params);

    ResponseEntity<List<ParkingPlace>> findAvailableParkingPlaces(LocalDate startDate, int numberOfDays, ParkingPlace.VehicleType vehicleType);

    ResponseEntity<?> deleteParkingPlace(int number);

    ResponseEntity<?> updateParkingPlace(int number, ParkingPlaceUpdateDto parkingPlace);

    ResponseEntity<?> createParkingPlace(ParkingPlace parkingPlace);
}
