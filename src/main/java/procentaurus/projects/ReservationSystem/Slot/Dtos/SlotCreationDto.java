package procentaurus.projects.ReservationSystem.Slot.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SlotCreationDto {
    private Space space;
    private LocalDate date;
}
