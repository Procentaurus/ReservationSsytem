package procentaurus.projects.hotelManager.ConferenceRoom;

import java.util.List;

public class ConferenceRoomFilter {

    public static List<ConferenceRoom> filterByHasStage(List<ConferenceRoom> data, String valueOfParam){

        List<ConferenceRoom> result;
        if (Boolean.parseBoolean(valueOfParam)) result = data.stream().filter(ConferenceRoom::isHasStage).toList();
        else result = data.stream().filter(room -> !room.isHasStage()).toList();

        return result;
    }
}
