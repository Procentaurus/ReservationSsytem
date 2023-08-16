package procentaurus.projects.ReservationSystem.Room.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.Room.Dtos.RoomUpdateDto;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RoomControllerInterface {

    ResponseEntity<?> findSingleRoom(int number);

    ResponseEntity<List<Room>> findRooms(Map<String, String> params);

    ResponseEntity<List<Room>> findAvailableRooms(LocalDate startDate, short numberOfDays, Room.RoomType standard, boolean viewForLake, boolean forSmokingPeople);

    ResponseEntity<?> deleteRoom(int number);

    ResponseEntity<?> updateRoom(int number, RoomUpdateDto room);

    ResponseEntity<?> createRoom(Room room);
}
