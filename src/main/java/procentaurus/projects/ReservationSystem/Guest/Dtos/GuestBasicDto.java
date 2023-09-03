package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GuestBasicDto {

    protected final String firstName;
    protected final String lastName;
    protected final LocalDate dateOfBirth;
    protected final Integer phoneNumber;
    protected final String email;
    protected final Boolean signedForNewsletter;

    public GuestBasicDto(Guest guest){
        this.email = guest.getEmail();
        this.firstName = guest.getFirstName();
        this.lastName = guest.getLastName();
        this.dateOfBirth = guest.getDateOfBirth();
        this.phoneNumber = guest.getPhoneNumber();
        this.signedForNewsletter = guest.getSignedForNewsletter();
    }
}