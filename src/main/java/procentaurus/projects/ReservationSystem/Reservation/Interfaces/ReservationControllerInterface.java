package procentaurus.projects.ReservationSystem.Reservation.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationControllerInterface {

    ResponseEntity<?> findSingleReservation(Long id);

    ResponseEntity<List<Reservation>> findReservations(Map<String, String> params);

    ResponseEntity<?> deleteReservation(Long id);

    ResponseEntity<?> createReservation(ReservationCreateDto creationDto);
}
