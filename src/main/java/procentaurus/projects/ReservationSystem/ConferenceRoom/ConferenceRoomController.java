package procentaurus.projects.ReservationSystem.ConferenceRoom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces.ConferenceRoomControllerInterface;
import procentaurus.projects.ReservationSystem.Exceptions.NonExistingConferenceRoomException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/conference_rooms")
public class ConferenceRoomController implements ConferenceRoomControllerInterface {

    private final ConferenceRoomService conferenceRoomService;

    @Autowired
    public ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = conferenceRoomService;
    }

    @Override
    @GetMapping(path = "/{number}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> findSingleConferenceRoom(@PathVariable int number) {
        Optional<ConferenceRoom> found =  conferenceRoomService.findSingleConferenceRoom(number);
        if(found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No conference room of provided number.");
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<ConferenceRoom>> findConferenceRooms(@RequestParam Map<String, String> params) {
        List<ConferenceRoom> found = conferenceRoomService.findConferenceRooms(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @GetMapping(path = "/available", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<ConferenceRoom>> findAvailableRooms(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "numberOfDays") int numberOfDays,
            @RequestParam(name = "hasStage", required = false) boolean hasStage) {
        List<ConferenceRoom> found = null;
        try {
            found = conferenceRoomService.findAvailableConferenceRooms(startDate, numberOfDays, hasStage);
        } catch (NonExistingConferenceRoomException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "/{number}", consumes = "application/json")
    public ResponseEntity<?> deleteConferenceRoom(@PathVariable int number) {
        boolean success = conferenceRoomService.deleteConferenceRoom(number);
        if(success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No conference room of provided number.");
    }

    @Override
    @PutMapping(path = "/{number}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateConferenceRoom(@PathVariable int number,@RequestParam Map<String, String> params) {
        Optional<ConferenceRoom> updated = conferenceRoomService.updateConferenceRoom(number, params);

        if(updated.isPresent()) return ResponseEntity.ok(updated);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No conference room of provided number or wrong params.");
    }

    @Override
    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createConferenceRoom(@RequestBody ConferenceRoom conferenceRoom) {
        Optional<ConferenceRoom> created = conferenceRoomService.createConferenceRoom(conferenceRoom);

        if(created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
