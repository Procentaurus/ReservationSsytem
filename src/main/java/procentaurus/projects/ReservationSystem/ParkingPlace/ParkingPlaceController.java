package procentaurus.projects.ReservationSystem.ParkingPlace;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Exceptions.NonExistingParkingPlaceException;
import procentaurus.projects.ReservationSystem.ParkingPlace.Dtos.ParkingPlaceUpdateDto;
import procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces.ParkingPlaceControllerInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/parking_places/")
public class ParkingPlaceController implements ParkingPlaceControllerInterface {

    private final ParkingPlaceService parkingPlaceService;

    @Autowired
    public ParkingPlaceController(ParkingPlaceService parkingPlaceService) {
        this.parkingPlaceService = parkingPlaceService;
    }

    @Override
    @GetMapping(path = "{number}/",produces = "application/json")
    public ResponseEntity<?> findSingleParkingPlace(@PathVariable int number) {
        Optional<ParkingPlace> found = parkingPlaceService.findSingleParkingPlace(number);
        if (found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No parking place of provided number.");
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ParkingPlace>> findParkingPlaces(@RequestParam Map<String, String> params) {
        List<ParkingPlace> found = parkingPlaceService.findParkingPlaces(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @GetMapping(path = "available/", produces = "application/json")
    public ResponseEntity<List<ParkingPlace>> findAvailableParkingPlaces(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "numberOfDays") int numberOfDays,
            @RequestParam(name = "vehicleType") ParkingPlace.VehicleType vehicleType) {

        List<ParkingPlace> found;
        try {
            found = parkingPlaceService.findAvailableParkingPlaces(startDate, numberOfDays, vehicleType);
        } catch (NonExistingParkingPlaceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "{number}/", produces = "application/json")
    public ResponseEntity<?> deleteParkingPlace(@PathVariable int number) {
        boolean success = parkingPlaceService.deleteParkingPlace(number);
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No parking place of provided number.");
    }

    @Override
    @PutMapping(path = "{number}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateParkingPlace(@PathVariable int number, @RequestBody ParkingPlaceUpdateDto parkingPlace) {
        Optional<ParkingPlace> updated = parkingPlaceService.updateParkingPlace(number, parkingPlace);

        if (updated.isPresent()) return ResponseEntity.ok(updated);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No parking place of provided number or wrong params.");
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createParkingPlace(@Valid @RequestBody ParkingPlace parkingPlace) {
        Optional<ParkingPlace> created = parkingPlaceService.createParkingPlace(parkingPlace);

        if (created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}