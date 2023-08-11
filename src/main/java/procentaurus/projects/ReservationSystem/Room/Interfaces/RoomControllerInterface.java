package procentaurus.projects.ReservationSystem.Room.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.util.List;
import java.util.Map;

public interface RoomControllerInterface {

    ResponseEntity<?> findSingleRoom(int number);

    ResponseEntity<List<Room>> findRooms(Map<String, String> params);

    ResponseEntity<?> deleteRoom(int number);

    ResponseEntity<?> updateRoom(int number, Map<String, String> params);

    ResponseEntity<?> createRoom(Room room);
}
