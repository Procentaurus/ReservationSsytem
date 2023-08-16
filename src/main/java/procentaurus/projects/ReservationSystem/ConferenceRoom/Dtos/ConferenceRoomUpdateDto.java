package procentaurus.projects.ReservationSystem.ConferenceRoom.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Room.Room;

@Getter
@AllArgsConstructor
public class ConferenceRoomUpdateDto {

    private final Integer capacity;
    private final Float price;
    private final Boolean hasStage;
    private final String description;

    public boolean isValid(){
        if(capacity != null)
            if(capacity > 1000 || capacity < 10) return false;
        if(price != null)
            if(price < 0) return false;
        if(description != null)
            return description.length() <= 200 && description.length() >= 10;

        return true;
    }
}
