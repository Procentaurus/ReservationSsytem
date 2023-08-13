package procentaurus.projects.ReservationSystem.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationControllerInterface;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController implements ReservationControllerInterface {

    public ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @Override
    public ResponseEntity<?> findSingleReservation(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Reservation>> findReservations(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteReservation(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> createReservation(Map<String, String> params) {
        return null;

        //TODO : check availability

        //TODO : check if some guests are already in base otherwise save them in db

        //TODO : create and save the reservation

        //TODO : book all of the slots
    }
}
