package procentaurus.projects.ReservationSystem.Reservation.Dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ReservationCreationDto {

    private final List<Integer> roomNumbers;
    private final List<Integer> conferenceRoomNumbers;
    private final List<Integer> parkingPlacesNumbers;

    private final List<Guest> guests;

    @FutureOrPresent
    private final LocalDate startDate;

    @Size( min = 1, max = 90)
    private final short numberOfDays;

    public ReservationCreationDto(List<Integer> roomNumbers, List<Integer> conferenceRoomNumbers, List<Integer> parkingPlacesNumbers,
                                  List<Guest> guests, LocalDate startDate, short numberOfDays) {
        this.roomNumbers = roomNumbers;
        this.conferenceRoomNumbers = conferenceRoomNumbers;
        this.parkingPlacesNumbers = parkingPlacesNumbers;
        this.guests = guests;
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
    }
}
