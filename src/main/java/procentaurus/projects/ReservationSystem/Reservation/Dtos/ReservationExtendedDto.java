package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestPermissionedStuffDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotLightDto;

import java.time.LocalDate;
import java.util.List;

public class ReservationExtendedDto {

    @FutureOrPresent
    private final LocalDate startDate;

    @Size( min = 1, max = 90)
    private final short numberOfDays;

    private final List<GuestPermissionedStuffDto> guests;
    private final List<SlotLightDto> occupiedSpaces;

    public ReservationExtendedDto(Reservation reservation) {

        this.guests = reservation.getGuests().stream().map(GuestPermissionedStuffDto::new).toList();
        this.startDate = reservation.getStartDate();
        this.numberOfDays = reservation.getNumberOfDays();
        this.occupiedSpaces = reservation.getOccupiedSlots().stream().map(SlotLightDto::new).toList();
    }
}
