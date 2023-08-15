package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;

@Getter
@Setter
public class SlotMediumDto extends SlotDto{
    protected int spaceId;

    public SlotMediumDto(Slot slot) {
        super(slot.getId(), slot.getDate(), slot.getStatus(), slot.getReservation().getId());

        if(slot.getConferenceRoom() != null)
            this.spaceId = slot.getConferenceRoom().getNumber();
        else if(slot.getRoom() != null)
            this.spaceId = slot.getRoom().getNumber();
        else if(slot.getParkingPlace() != null)
            this.spaceId = slot.getParkingPlace().getNumber();
    }
}