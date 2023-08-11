package procentaurus.projects.hotelManager.Reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import procentaurus.projects.hotelManager.Guest.Guest;
import procentaurus.projects.hotelManager.Room.Room;
import procentaurus.projects.hotelManager.Slot.Slot;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private int numberOfParkingPlacesForMotorcycles;

    @Positive
    private int numberOfParkingPlacesForAutocars;

    @Positive
    private int numberOfParkingPlacesForCars;

    private boolean onlyRooms;

    private boolean onlyConferenceRoom;

    private boolean forSmokingPeople;

    private boolean viewForLake;

    @NotNull
    private Room.RoomType chosenRoomStandard;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @Size( min = 1, max = 90)
    private short numberOfDays;

    @OneToMany(mappedBy = "reservation")
    private List<Guest> guests;

    @OneToMany(mappedBy = "reservation")
    private List<Slot> occupiedSlots;
}
