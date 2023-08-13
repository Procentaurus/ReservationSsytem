package procentaurus.projects.ReservationSystem.Reservation.Interfaces;

import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationServiceInterface {
    Optional<Reservation> findSingleReservation(Long id);

    List<Reservation> findReservations(Map<String, String> params);

    boolean deleteReservation(Long id);

    Optional<Reservation> createReservation(List<Integer> roomsNumbers, List<Integer> conferenceRoomsNumbers, List<Integer> parkingPlacesNumbers,
                                            List<Guest> guests, LocalDate startDate, short numberOfDays);

    Optional<Reservation> createReservation(List<Integer> roomsNumbers, short sizeOfConferenceRoom, short numberOfParkingPlaces,
                                            List<Guest> guests, LocalDate startDate, short numberOfDays);
}
