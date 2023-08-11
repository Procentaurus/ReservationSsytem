package procentaurus.projects.hotelManager.Room;

import procentaurus.projects.hotelManager.Exceptions.NonExistingRoomException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomServiceInterface {
    Optional<Room> findSingleRoom(int number);

    List<Room> findRooms(Map<String, String> params);

    List<Room> findAvailableRooms(LocalDate startDate, short numberOfDays, Room.RoomType standard, boolean viewForLake, boolean forSmokingPeople)
            throws NonExistingRoomException;

    boolean deleteRoom(int number);

    Optional<Room> updateRoom(int number, Map<String, String> params);

    Optional<Room> createRoom(Room parkingPlace);
}
