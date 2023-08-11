package procentaurus.projects.hotelManager.Guest.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.hotelManager.Guest.Guest;

import java.time.LocalDate;

@Getter
@Setter
public class GuestCustomerDto extends GuestDto{
    private boolean signedForNewsletter;

    public GuestCustomerDto(Guest guest) {
        super(guest.getFirstName(), guest.getLastName(), guest.getDateOfBirth(), guest.getPhoneNumber(), guest.getEmail());
        this.signedForNewsletter = guest.isSignedForNewsletter();
    }
}
