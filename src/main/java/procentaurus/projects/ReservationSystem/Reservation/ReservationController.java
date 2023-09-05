package procentaurus.projects.ReservationSystem.Reservation;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Miscellaneous.AuthorityChecker;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationAdminDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationExtendedDto;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationMediumDto;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationControllerInterface;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationRepository;

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
    public ResponseEntity<?> findSingleReservation(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Reservation> found = reservationService.findSingleReservation(id);

        if (found.isPresent()){
            AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
            if(authorityChecker.hasAdminAuthority())
                return ResponseEntity.ok(new ReservationAdminDto(found.get()));
            else if(authorityChecker.hasConciergeAuthority() || authorityChecker.hasManagerAuthority())
                return ResponseEntity.ok(new ReservationExtendedDto(found.get()));
            else
                return ResponseEntity.ok(new ReservationMediumDto(found.get()));
        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation of provided number.");
    }

    @Override
    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<?>> findReservations(
            @RequestParam Map<String, String> params, @AuthenticationPrincipal UserDetails userDetails) {

        List<Reservation> reservations = reservationService.findReservations(params);

        AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
        if(authorityChecker.hasAdminAuthority()) {
            if (reservations.isEmpty()) return ResponseEntity.ok(null);
            else return ResponseEntity.ok(reservations.stream().map(ReservationAdminDto::new).toList());
        }
        else if(authorityChecker.hasConciergeAuthority() || authorityChecker.hasManagerAuthority()) {
            if (reservations.isEmpty()) return ResponseEntity.ok(null);
            else return ResponseEntity.ok(reservations.stream().map(ReservationExtendedDto::new).toList());
        }
        else {
            if (reservations.isEmpty()) return ResponseEntity.ok(null);
            else return ResponseEntity.ok(reservations.stream().map(ReservationMediumDto::new).toList());
        }
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
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationCreateDto creationDto, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Reservation> created = reservationService.createReservation(creationDto);

        if(created.isPresent()){
            AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
            if(authorityChecker.hasAdminAuthority())
                return ResponseEntity.status(HttpStatus.CREATED).body(new ReservationAdminDto(created.get()));
            else if(authorityChecker.hasConciergeAuthority() || authorityChecker.hasManagerAuthority())
                return ResponseEntity.status(HttpStatus.CREATED).body(new ReservationExtendedDto(created.get()));
            else
                return ResponseEntity.status(HttpStatus.CREATED).body(new ReservationMediumDto(created.get()));
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
