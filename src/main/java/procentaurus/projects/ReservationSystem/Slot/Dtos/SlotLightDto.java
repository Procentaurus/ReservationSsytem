package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.Getter;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;

@Getter
public class SlotLightDto {

    private final Long id;
    private SpaceType spaceType;

    public SlotLightDto(Slot slot){
        this.id = slot.getId();

        if(slot.getConferenceRoom() != null) this.spaceType = SpaceType.CONFERENCE_ROOM;
        else if(slot.getRoom() != null) this.spaceType = SpaceType.ROOM;
        else if(slot.getParkingPlace() != null) this.spaceType = SpaceType.PARKING_PLACE;
    }
    public enum SpaceType{
        ROOM,
        CONFERENCE_ROOM,
        PARKING_PLACE,
    }
}