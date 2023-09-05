package procentaurus.projects.ReservationSystem.Reservation.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationControllerInterface {

    ResponseEntity<?> findSingleReservation(Long id, UserDetails userDetails);

    ResponseEntity<List<?>> findReservations(Map<String, String> params, UserDetails userDetails);

    ResponseEntity<?> deleteReservation(Long id);

    ResponseEntity<?> createReservation(ReservationCreateDto creationDto, UserDetails userDetails);
}
