package procentaurus.projects.hotelManager.ParkingPlace;

import procentaurus.projects.hotelManager.Exceptions.NonExistingParkingPlaceException;
import procentaurus.projects.hotelManager.Exceptions.NonExistingRoomException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParkingPlaceServiceInterface {

    Optional<ParkingPlace> findSingleParkingPlace(int number);

    List<ParkingPlace> findParkingPlaces(Map<String, String> params);

    List<ParkingPlace> findAvailableParkingPlaces() throws NonExistingParkingPlaceException;

    boolean deleteParkingPlace(int number);

    Optional<ParkingPlace> updateParkingPlace(int number, Map<String, String> params);

    Optional<ParkingPlace> createParkingPlace(ParkingPlace parkingPlace);
}
