package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class ReservationCreationDto {

    private final Set<Integer> roomNumbers;
    private final Set<Integer> conferenceRoomNumbers;
    private final Set<Integer> parkingPlacesNumbers;

    private final Set<Guest> guests;

    @FutureOrPresent
    private final LocalDate startDate;

    @Size( min = 1, max = 90)
    private final short numberOfDays;

    public ReservationCreationDto(Set<Integer> roomNumbers, Set<Integer> conferenceRoomNumbers, Set<Integer> parkingPlacesNumbers,
                                  Set<Guest> guests, LocalDate startDate, short numberOfDays) {
        this.roomNumbers = roomNumbers;
        this.conferenceRoomNumbers = conferenceRoomNumbers;
        this.parkingPlacesNumbers = parkingPlacesNumbers;
        this.guests = guests;
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
    }
}
