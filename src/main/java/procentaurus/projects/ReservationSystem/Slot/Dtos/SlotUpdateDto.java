package procentaurus.projects.ReservationSystem.Slot.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.Slot.Slot;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SlotUpdateDto {

    @NotNull
    private Slot.Status status;
}
