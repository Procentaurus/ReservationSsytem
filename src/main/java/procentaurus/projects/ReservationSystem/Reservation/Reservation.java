package procentaurus.projects.ReservationSystem.Reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Slot.Slot;

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

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @Size( min = 1, max = 90)
    private short numberOfDays;

    @ManyToMany(mappedBy = "reservation")
    private List<Guest> guests;

    @OneToMany(mappedBy = "reservation")
    private List<Slot> occupiedSlots;
}
