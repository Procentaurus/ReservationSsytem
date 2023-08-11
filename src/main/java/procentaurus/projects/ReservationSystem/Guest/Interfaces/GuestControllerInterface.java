package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.GuestResponse;

import java.util.List;

public interface GuestControllerInterface {

    ResponseEntity<GuestResponse> getSingleGuest(Long id);

    ResponseEntity<List<Guest>> getMultipleGuests(MultiValueMap<String, String> params);

    ResponseEntity<GuestResponse> deleteGuest(Long id);

    ResponseEntity<GuestResponse> updateGuest(Long id, Guest guest);

    ResponseEntity<GuestResponse> createGuest(Guest guest);

}
