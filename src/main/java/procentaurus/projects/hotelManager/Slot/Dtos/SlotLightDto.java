package procentaurus.projects.hotelManager.Slot.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.hotelManager.Slot.Slot;

@Getter
@Setter
public class SlotLightDto extends SlotDto{
    protected Long spaceId;

    public SlotLightDto(Slot slot) {
        super(slot.getId(), slot.getDate(), slot.getStatus(), slot.getReservation().getId());
        this.spaceId = spaceId;
    }
}