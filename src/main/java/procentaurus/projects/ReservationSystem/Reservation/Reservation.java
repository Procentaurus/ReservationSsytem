package procentaurus.projects.ReservationSystem.Reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.time.LocalDate;
import java.util.Set;

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

    @Min(1)
    @Max(90)
    private short numberOfDays;

    @ManyToMany
    @JoinTable(name = "reservation_guest",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set<Guest> guests;

    @OneToMany(mappedBy = "reservation")
    private Set<Slot> occupiedSlots;

    public Reservation(@NotNull LocalDate startDate, short numberOfDays, Set<Guest> guests, Set<Slot> occupiedSlots) {
        this.startDate = startDate;
        this.numberOfDays = numberOfDays;
        this.guests = guests;
        this.occupiedSlots = occupiedSlots;
    }
}
