package procentaurus.projects.ReservationSystem.Slot;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.Room.Room;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;

@Entity
@Data
@Table(name="slots")
@NoArgsConstructor(force = true)
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    @NotNull
    private final LocalDate date;

    @NotNull
    private Status status;

    @ManyToOne
    @JoinColumn(name = "roomNumber")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "parkingPlaceNumber")
    private ParkingPlace parkingPlace;

    @ManyToOne
    @JoinColumn(name = "conferenceRoomNumber")
    private ConferenceRoom conferenceRoom;

    @ManyToOne
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    public enum Status{
        FREE,
        BOOKED,
        IN_USE,
        IN_RENOVATION;
    }

    public enum SpaceType{
        ROOM,
        CONFERENCE_ROOM,
        PARKING_PLACE,
    }

    public Slot(Space space, @NotNull LocalDate date) {
        this.date = date;
        this.status = Status.FREE;
        this.reservation = null;

        this.conferenceRoom = null;
        this.parkingPlace = null;
        this.room = null;

        if(space instanceof Room) this.room = (Room) space;
        if(space instanceof ConferenceRoom) this.conferenceRoom = (ConferenceRoom) space;
        if(space instanceof ParkingPlace) this.parkingPlace = (ParkingPlace) space;
    }
}
