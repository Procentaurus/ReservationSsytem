package procentaurus.projects.ReservationSystem.Reservation.Interfaces;

import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationAdminDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationExtendedDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationMediumDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ReservationServiceInterface {
    Optional<Reservation> findSingleReservation(Long id);

    List<Reservation> findReservations(Map<String, String> params);

    boolean deleteReservation(Long id);

    Optional<Reservation> createReservation(ReservationCreateDto creationDto);

    Optional<Reservation> createReservation(Set<Integer> roomsNumbers, short sizeOfConferenceRoom, short numberOfParkingPlaces,
                                            Set<Guest> guests, LocalDate startDate, short numberOfDays);
}
