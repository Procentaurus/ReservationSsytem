package procentaurus.projects.hotelManager.Room;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomServiceInterface {
    Optional<Room> findSingleRoom(int number);

    List<Room> findRooms(Map<String, String> params);

    boolean deleteRoom(int number);

    Optional<Room> updateRoom(int number, Map<String, String> params);

    Optional<Room> createRoom(Room parkingPlace);
}
