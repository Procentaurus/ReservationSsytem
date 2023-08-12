package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;

@Getter
@Setter
public class SlotLightDto extends SlotDto{
    protected Long spaceId;

    public SlotLightDto(Slot slot) {
        super(slot.getId(), slot.getDate(), slot.getStatus(), slot.getReservation().getId());

        if(slot.getConferenceRoom() != null)
            this.spaceId = slot.getConferenceRoom().getId();
        else if(slot.getRoom() != null)
            this.spaceId = slot.getRoom().getId();
        else if(slot.getParkingPlace() != null)
            this.spaceId = slot.getParkingPlace().getId();
    }
}