package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Guest.GuestResponse;

import java.util.List;
import java.util.Map;

public interface GuestControllerInterface {

    ResponseEntity<GuestResponse> getSingleGuest(Long id, UserDetails userDetails);

    ResponseEntity<List<? extends GuestBasicDto>> getGuests(Map<String, String> params, UserDetails userDetails);

    ResponseEntity<GuestResponse> deleteGuest(Long id);

    ResponseEntity<GuestResponse> updateGuest(Long id, GuestBasicDto guest);

    ResponseEntity<GuestResponse> createGuest(GuestBasicDto guest);

}
