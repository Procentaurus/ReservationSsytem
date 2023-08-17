package procentaurus.projects.ReservationSystem.Slot.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SlotCreationDto {

    @NotNull
    private Integer numberOfSpace;

    @NotNull
    private Slot.SpaceType type;

    @NotNull
    private LocalDate date;
}
