package procentaurus.projects.ReservationSystem.Guest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;
import procentaurus.projects.ReservationSystem.MyUser.MyUser;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "guests")
@Getter
@Setter
@NoArgsConstructor
public class Guest extends MyUser implements Comparable<Guest>{

    private Boolean signedForNewsletter;

    //private List<TroubleCausedByGuest> troubleCaused;

    @JsonIgnore
    @ManyToMany(mappedBy = "guests")
    private List<Reservation> reservations;

    public Guest(String firstName, String lastName, String password, LocalDate dateOfBirth, int phoneNumber, String email, @NotNull Boolean signedForNewsletter) {
        super(firstName, lastName, password, dateOfBirth, phoneNumber, email);
        //this.troubleCaused = new LinkedList<>();
        this.signedForNewsletter = signedForNewsletter;
    }
    public Guest(String firstName, String lastName, String password, LocalDate dateOfBirth, int phoneNumber, String email) {
        super(firstName, lastName, password, dateOfBirth, phoneNumber, email);
        //this.troubleCaused = new LinkedList<>();
        this.signedForNewsletter = false;
    }

    public Guest(GuestBasicDto guest){
        this.email = guest.getEmail();
        this.firstName = guest.getFirstName();
        this.lastName = guest.getLastName();
        this.dateOfBirth = guest.getDateOfBirth();
        this.phoneNumber = guest.getPhoneNumber();
        this.signedForNewsletter = guest.getSignedForNewsletter() != null && guest.getSignedForNewsletter();
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
