package procentaurus.projects.ReservationSystem.Reservation;

import procentaurus.projects.ReservationSystem.Exceptions.NonExistingRoomException;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationServiceInterface {
    Optional<Reservation> findSingleReservation(int number);

    List<Reservation> findReservations(Map<String, String> params);

    boolean deleteReservation(int number);

    Optional<Reservation> updateReservation(int number, Map<String, String> params);

    Optional<Reservation> createReservation(Room parkingPlace);
}
