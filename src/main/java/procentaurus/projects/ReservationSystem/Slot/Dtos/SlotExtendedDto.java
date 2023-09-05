package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;


@Getter
@Setter
public class SlotExtendedDto extends SlotDto{
    protected Space space;

    public SlotExtendedDto(Slot slot) {
        super(slot.getDate(), slot.getStatus(), slot.getReservation() != null ? slot.getReservation().getId() : null);

        if(slot.getConferenceRoom() != null)
            this.space = slot.getConferenceRoom();
        else if(slot.getRoom() != null)
            this.space = slot.getRoom();
        else if(slot.getParkingPlace() != null)
            this.space = slot.getParkingPlace();
    }
}
