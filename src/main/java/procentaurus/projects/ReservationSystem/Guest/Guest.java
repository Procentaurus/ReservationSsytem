package procentaurus.projects.ReservationSystem.Guest;

import jakarta.persistence.*;

import lombok.*;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.User.User;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "guests")
@Data
@NoArgsConstructor
public class Guest extends User implements Comparable<Guest>{

    private Boolean signedForNewsletter;

    //private List<TroubleCausedByGuest> troubleCaused;

    @ManyToMany(mappedBy = "guests")
    private List<Reservation> reservations;

    public Guest(String firstName, String lastName, String password, LocalDate dateOfBirth, int phoneNumber, String email, boolean signedForNewsletter) {
        super(firstName, lastName, password, dateOfBirth, phoneNumber, email);
        //this.troubleCaused = new LinkedList<>();
        this.signedForNewsletter = signedForNewsletter;
    }
    public Guest(String firstName, String lastName, String password, LocalDate dateOfBirth, int phoneNumber, String email) {
        super(firstName, lastName, password, dateOfBirth, phoneNumber, email);
        //this.troubleCaused = new LinkedList<>();
        this.signedForNewsletter = false;
    }

    @Override
    public int compareTo(Guest otherGuest) {
        int result = this.lastName.compareTo(otherGuest.lastName);

        if (result == 0) result = this.firstName.compareTo(otherGuest.firstName);
        else return result;

        if (result == 0) result = this.email.compareTo(otherGuest.email);
        else return result;

        return result;
    }
}
