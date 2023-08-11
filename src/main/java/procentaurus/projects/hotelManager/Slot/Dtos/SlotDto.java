package procentaurus.projects.hotelManager.Slot.Dtos;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import procentaurus.projects.hotelManager.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.hotelManager.ParkingPlace.ParkingPlace;
import procentaurus.projects.hotelManager.Reservation.Reservation;
import procentaurus.projects.hotelManager.Room.Room;
import procentaurus.projects.hotelManager.Slot.Slot;
import procentaurus.projects.hotelManager.Space.Space;

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