package procentaurus.projects.ReservationSystem.Room;

import java.util.List;

public class RoomFilter {

    public static List<Room> filterByIsSmokingAllowed(List<Room> data, String valueOfParam){

        List<Room> result;
        if (Boolean.parseBoolean(valueOfParam)) result = data.stream().filter(Room::getIsSmokingAllowed).toList();
        else result = data.stream().filter(room -> !room.getIsSmokingAllowed()).toList();

        return result;
    }

    public static List<Room> filterByHasLakeView(List<Room> data, String valueOfParam){

        List<Room> result;
        if (Boolean.parseBoolean(valueOfParam)) result = data.stream().filter(Room::getHasLakeView).toList();
        else result = data.stream().filter(room -> !room.getHasLakeView()).toList();

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
