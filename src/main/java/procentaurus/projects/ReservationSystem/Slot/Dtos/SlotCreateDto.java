package procentaurus.projects.ReservationSystem.Slot.Dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SlotCreateDto {

    @NotNull
    private Integer numberOfSpace;

    @NotNull
    private Slot.SpaceType type;

    @NotNull
    private LocalDate date;

    private Short numberOfDays;

    public SlotCreateDto(@NotNull Integer numberOfSpace, @NotNull Slot.SpaceType type, @NotNull LocalDate date) {
        this.numberOfSpace = numberOfSpace;
        this.type = type;
        this.date = date;
    }
}
