package procentaurus.projects.ReservationSystem.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Exceptions.NonExistingRoomException;
import procentaurus.projects.ReservationSystem.Room.Interfaces.RoomControllerInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/rooms")
public class RoomController implements RoomControllerInterface {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    @GetMapping(path = "/{number}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findSingleRoom(@PathVariable int number) {
        Optional<Room> found = roomService.findSingleRoom(number);
        if (found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number.");
    }

    @Override
    @GetMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Room>> findRooms(@RequestParam Map<String, String> params) {
        List<Room> found = roomService.findRooms(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @GetMapping(path = "/available", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Room>> findAvailableRooms(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "numberOfDays") short numberOfDays,
            @RequestParam(name = "standard", required = false) Room.RoomType standard,
            @RequestParam(name = "viewForLake", required = false) boolean viewForLake,
            @RequestParam(name = "forSmokingPeople", required = false) boolean forSmokingPeople) {

        List<Room> found = null;
        try {
            found = roomService.findAvailableRooms(startDate, numberOfDays, standard, viewForLake, forSmokingPeople);
        } catch (NonExistingRoomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "/{number}", consumes = "application/json")
    public ResponseEntity<?> deleteRoom(@PathVariable int number) {
        boolean success = roomService.deleteRoom(number);
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number.");
    }

    @Override
    @PutMapping(path = "/{number}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateRoom(@PathVariable int number, @RequestBody Room room) {
        Optional<Room> updated = roomService.updateRoom(number, room);

        if (updated.isPresent()) return ResponseEntity.ok(updated);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number or wrong params.");
    }

    @Override
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        Optional<Room> created = roomService.createRoom(room);

        if (created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
