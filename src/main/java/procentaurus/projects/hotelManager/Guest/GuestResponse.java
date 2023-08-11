package procentaurus.projects.hotelManager.Guest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.hotelManager.Guest.Dtos.GuestDto;

@Getter
@Setter
@AllArgsConstructor
public class GuestResponse {

    private GuestDto guestDto;
    private String message;

    public GuestResponse(GuestDto guestDto){
        this.guestDto = guestDto;
        this.message = "";
    }
}
