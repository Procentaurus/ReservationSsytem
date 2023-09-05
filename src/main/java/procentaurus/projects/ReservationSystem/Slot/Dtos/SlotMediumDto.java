package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;

@Getter
@Setter
public class SlotMediumDto extends SlotDto{
    protected int spaceId;
    protected Slot.SpaceType type;

    public SlotMediumDto(Slot slot) {
        super(slot.getDate(), slot.getStatus(), slot.getReservation() != null ? slot.getReservation().getId() : null);

        if(slot.getConferenceRoom() != null) {
            this.spaceId = slot.getConferenceRoom().getNumber();
            this.type = Slot.SpaceType.CONFERENCE_ROOM;
        }
        else if(slot.getRoom() != null) {
            this.spaceId = slot.getRoom().getNumber();
            this.type = Slot.SpaceType.ROOM;
        }
        else if(slot.getParkingPlace() != null) {
            this.spaceId = slot.getParkingPlace().getNumber();
            this.type = Slot.SpaceType.PARKING_PLACE;
        }
    }
}