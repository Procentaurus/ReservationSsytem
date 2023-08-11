package procentaurus.projects.ReservationSystem.Guest;

import jakarta.persistence.*;

import lombok.*;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.User.User;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "guests")
@Data
@NoArgsConstructor
public class Guest extends User implements Comparable<Guest>{

    private boolean signedForNewsletter;

    //private List<TroubleCausedByGuest> troubleCaused;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

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
