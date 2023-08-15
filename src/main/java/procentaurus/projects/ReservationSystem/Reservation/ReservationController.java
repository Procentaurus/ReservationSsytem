package procentaurus.projects.ReservationSystem.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations/")
public class ReservationController implements ReservationControllerInterface {

    public ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    @GetMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findSingleReservation(@PathVariable Long id) {

        Optional<Reservation> found = reservationService.findSingleReservation(id);

        if (found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation of provided number.");
    }

    @Override
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Reservation>> findReservations(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(reservationService.findReservations(params));
    }

    @Override
    @DeleteMapping(path = "{id}/", consumes = "application/json")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {

        boolean success = reservationService.deleteReservation(id);
        if(success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation of provided id.");
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createReservation(@RequestBody ReservationCreateDto creationDto) {

        Optional<Reservation> created = reservationService.createReservation(creationDto);

        if(created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
