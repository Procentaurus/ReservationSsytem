package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.Trouble.TroubleCausedByGuest;

import java.util.List;

@Getter
@Setter
public class GuestManagerDto extends GuestDto {
    private Long id;
    private boolean signedForNewsletter;
    private List<TroubleCausedByGuest> troubleCaused;

    public GuestManagerDto(Guest guest) {
        super(guest.getFirstName(), guest.getLastName(), guest.getDateOfBirth(), guest.getPhoneNumber(), guest.getEmail());
        this.id = guest.getId();
        this.signedForNewsletter = guest.isSignedForNewsletter();
        //this.troubleCaused = guest.getTroubleCaused();
    }
}
