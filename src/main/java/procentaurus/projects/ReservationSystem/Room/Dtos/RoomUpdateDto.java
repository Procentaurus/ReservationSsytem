package procentaurus.projects.ReservationSystem.Room.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Room.Room;

@Getter
@AllArgsConstructor
public class RoomUpdateDto {

    private final Integer capacity;
    private final Float price;
    private final Room.RoomType roomType;
    private final Boolean isSmokingAllowed;
    private final Boolean hasLakeView;
    private final String description;

    public boolean isValid(){
        if(capacity != null)
            if(capacity > 4 || capacity < 1) return false;
        if(price != null)
            if(price < 0) return false;
        if(description != null)
            return description.length() <= 200 && description.length() >= 10;

        return true;
    }
}
