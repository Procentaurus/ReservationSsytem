package procentaurus.projects.hotelManager.Room;

import procentaurus.projects.hotelManager.ParkingPlace.ParkingPlace;

import java.util.List;

public class RoomFilter {

    public static List<Room> filterByIsSmokingAlllowed(List<Room> data, String valueOfParam){

        List<Room> result;
        if (Boolean.parseBoolean(valueOfParam)) result = data.stream().filter(Room::isSmokingAllowed).toList();
        else result = data.stream().filter(room -> !room.isSmokingAllowed()).toList();

        return result;
    }

    public static List<Room> filterByHasLakeView(List<Room> data, String valueOfParam){

        List<Room> result;
        if (Boolean.parseBoolean(valueOfParam)) result = data.stream().filter(Room::isHasLakeView).toList();
        else result = data.stream().filter(room -> !room.isHasLakeView()).toList();

        return result;
    }

    public static List<Room> filterByRoomType(List<Room> data, Room.RoomType type){
        return data.stream().filter(x -> x.getRoomType() == type).toList();
    }

    public static boolean isFilteringByRoomTypePossible(String data){

        Room.RoomType type;
        try{
            type = Room.RoomType.valueOf(data.toUpperCase());
        }catch(IllegalArgumentException e){
            return false;
        }

        return true;
    }
}
