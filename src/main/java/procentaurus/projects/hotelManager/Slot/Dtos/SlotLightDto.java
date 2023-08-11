package procentaurus.projects.hotelManager.Slot.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import procentaurus.projects.hotelManager.Slot.Slot;

import java.time.LocalDate;

@Getter
@Setter
public class SlotLightDto extends SlotDto{
    protected Long spaceId;

    public SlotLightDto(Slot slot) {
        super(slot.getId(), slot.getDate(), slot.getStatus(), slot.getReservation().getId());
        this.spaceId = spaceId;
    }
}