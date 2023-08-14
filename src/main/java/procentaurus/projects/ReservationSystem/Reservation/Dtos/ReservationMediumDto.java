package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotLightDto;

import java.time.LocalDate;
import java.util.List;

public class ReservationMediumDto {

    @FutureOrPresent
    private final LocalDate startDate;

    @Size( min = 1, max = 90)
    private final short numberOfDays;

    private final List<GuestBasicDto> guests;
    private final List<SlotLightDto> occupiedSpaces;

    public ReservationMediumDto(Reservation reservation) {

        this.guests = reservation.getGuests().stream().map(GuestBasicDto::new).toList();
        this.startDate = reservation.getStartDate();
        this.numberOfDays = reservation.getNumberOfDays();
        this.occupiedSpaces = reservation.getOccupiedSlots().stream().map(SlotLightDto::new).toList();
    }
}
