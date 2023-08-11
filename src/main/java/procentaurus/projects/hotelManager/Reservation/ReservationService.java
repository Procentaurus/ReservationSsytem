package procentaurus.projects.hotelManager.Reservation;

import procentaurus.projects.hotelManager.Room.Room;
import procentaurus.projects.hotelManager.Room.RoomServiceInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReservationService implements RoomServiceInterface {
    @Override
    public Optional<Room> findSingleRoom(int number) {
        return Optional.empty();
    }

    @Override
    public List<Room> findRooms(Map<String, String> params) {
        return null;
    }

    @Override
    public boolean deleteRoom(int number) {
        return false;
    }

    @Override
    public Optional<Room> updateRoom(int number, Map<String, String> params) {
        return Optional.empty();
    }

    @Override
    public Optional<Room> createRoom(Room parkingPlace) {
        return Optional.empty();
    }
}
