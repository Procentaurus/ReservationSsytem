package procentaurus.projects.ReservationSystem.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController implements ReservationControllerInterface {

    public ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findSingleReservation(@PathVariable Long id) {

        Optional<Reservation> found = reservationService.findSingleReservation(id);

        if (found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation of provided number.");
    }

    @Override
    @GetMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Reservation>> findReservations(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(reservationService.findReservations(params));
    }

    @Override
    @GetMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        return null;

        //TODO : check existence

        //TODO : unbook all slots

        //TODO : delete reservation
    }

    @Override
    @GetMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createReservation(@RequestBody Map<String, String> params) {
        return null;

        //TODO : check availability

        //TODO : check if some guests are already in base otherwise save them in db

        //TODO : create and save the reservation

        //TODO : book all of the slots
    }
}
