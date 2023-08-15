package procentaurus.projects.ReservationSystem.Slot.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SlotCreationDto {

    @NotNull
    private Space space;

    @NotNull
    private LocalDate date;
}
