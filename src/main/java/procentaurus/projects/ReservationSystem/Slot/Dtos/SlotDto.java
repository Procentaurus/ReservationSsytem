package procentaurus.projects.ReservationSystem.Slot.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public abstract class SlotDto {
    protected Long id;
    protected LocalDate date;
    protected Slot.Status status;
    protected Long reservationId;
}