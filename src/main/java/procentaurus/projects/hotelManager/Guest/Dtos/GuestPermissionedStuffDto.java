package procentaurus.projects.hotelManager.Guest.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.hotelManager.Guest.Guest;
import procentaurus.projects.hotelManager.Guest.Trouble.TroubleCausedByGuest;

import java.util.List;

@Getter
@Setter
public class GuestPermissionedStuffDto extends GuestDto{

    private List<TroubleCausedByGuest> troubleCaused;

    public GuestPermissionedStuffDto(Guest guest) {
        super(guest.getFirstName(), guest.getLastName(), guest.getDateOfBirth(), guest.getPhoneNumber(), guest.getEmail());
        //this.troubleCaused = guest.getTroubleCaused();
    }
}
