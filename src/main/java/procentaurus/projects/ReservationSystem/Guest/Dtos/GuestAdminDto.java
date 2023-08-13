package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.TroubleCaused.TroubleCausedByGuest;

import java.util.List;

@Getter
@Setter
public class GuestAdminDto extends GuestDto {
    private Long id;
    private List<TroubleCausedByGuest> troubleCaused;

    public GuestAdminDto(Guest guest) {
        super(guest.getFirstName(), guest.getLastName(), guest.getDateOfBirth(), guest.getPhoneNumber(), guest.getEmail(), guest.isSignedForNewsletter());
        this.id = guest.getId();
        //this.troubleCaused = guest.getTroubleCaused();
    }
}
