package procentaurus.projects.hotelManager.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RoomController implements RoomControllerInterface{

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public ResponseEntity<?> findSingleRoom(int number) {
        Optional<Room> found = roomService.findSingleRoom(number);
        if (found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number.");
    }

    @Override
    public ResponseEntity<List<Room>> findRooms(Map<String, String> params) {
        List<Room> found = roomService.findRooms(params);
        return ResponseEntity.ok(found);
    }

    @Override
    public ResponseEntity<?> deleteRoom(int number) {
        boolean success = roomService.deleteRoom(number);
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number.");
    }

    @Override
    public ResponseEntity<?> updateRoom(int number, Map<String, String> params) {
        Optional<Room> updated = roomService.updateRoom(number, params);

        if (updated.isPresent()) return ResponseEntity.ok(updated);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room of provided number or wrong params.");
    }

    @Override
    public ResponseEntity<?> createRoom(Room room) {
        Optional<Room> created = roomService.createRoom(room);

        if (created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
