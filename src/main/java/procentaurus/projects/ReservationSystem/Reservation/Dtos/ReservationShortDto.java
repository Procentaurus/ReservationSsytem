package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotLightDto;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReservationShortDto {

    @FutureOrPresent
    private LocalDate startDate;

    @Size( min = 1, max = 90)
    private short numberOfDays;

    private List<Integer> guestsIds;
    private List<String> guestsEmails;
    private List<Integer> reservationsIds;


    public ReservationShortDto(Reservation reservation){
        this.guestsEmails = reservation.getGuests().stream().map(Guest::getEmail).toList();
//        this.reservationsIds = reservation.getOccupiedSlots().stream().map(slot -> SlotLightDto)
//    }
}
