package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.TroubleCaused.TroubleCausedByGuest;

import java.util.List;
import java.util.SortedSet;

@Getter
@Setter
public class GuestAdminDto extends GuestBasicDto {
    private Long id;
    private SortedSet<TroubleCausedByGuest> troubleCaused;

    public GuestAdminDto(Guest guest) {
        super(guest);
        this.id = guest.getId();
        //this.troubleCaused = guest.getTroubleCaused();
    }
}
