package procentaurus.projects.ReservationSystem.Guest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;

@Getter
@Setter
@AllArgsConstructor
public class GuestResponse {

    private GuestBasicDto guestDto;
    private String message;

    public GuestResponse(GuestBasicDto guestDto){
        this.guestDto = guestDto;
        this.message = "";
    }
}
