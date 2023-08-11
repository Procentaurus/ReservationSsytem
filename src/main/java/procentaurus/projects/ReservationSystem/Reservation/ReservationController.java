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
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findSingleReservation(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping(path = "/")
    public ResponseEntity<List<Reservation>> findReservations(Map<String, String> params) {
        return null;
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        return null;
    }

    @Override
    @PostMapping(path = "/")
    public ResponseEntity<?> createReservation(Reservation reservation) {
        return null;
    }
}
