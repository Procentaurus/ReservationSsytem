package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestPermissionedStuffDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotLightDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationExtendedDto {

    @FutureOrPresent
    @NotNull
    private final LocalDate startDate;

    @Size( min = 1, max = 90)
    private final short numberOfDays;

    private final Set<GuestPermissionedStuffDto> guests;
    private final Set<SlotLightDto> occupiedSpaces;

    public ReservationExtendedDto(Reservation reservation) {

        this.guests = reservation.getGuests().stream().map(GuestPermissionedStuffDto::new).collect(Collectors.toSet());
        this.startDate = reservation.getStartDate();
        this.numberOfDays = reservation.getNumberOfDays();
        this.occupiedSpaces = reservation.getOccupiedSlots().stream().map(SlotLightDto::new).collect(Collectors.toSet());
    }
}
