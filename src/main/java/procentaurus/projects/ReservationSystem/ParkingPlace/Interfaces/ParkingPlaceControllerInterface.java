package procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;

import java.util.List;
import java.util.Map;

public interface ParkingPlaceControllerInterface {

    ResponseEntity<?> findSingleParkingPlace(int number);

    ResponseEntity<List<ParkingPlace>> findParkingPlaces(Map<String, String> params);

    ResponseEntity<?> deleteParkingPlace(int number);

    ResponseEntity<?> updateParkingPlace(int number, Map<String, String> params);

    ResponseEntity<?> createParkingPlace(ParkingPlace parkingPlace);
}
