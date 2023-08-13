package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.GuestResponse;

import java.util.List;
import java.util.Map;

public interface GuestControllerInterface {

    ResponseEntity<GuestResponse> getSingleGuest(Long id);

    ResponseEntity<List<Guest>> getGuests(Map<String, String> params);

    ResponseEntity<GuestResponse> deleteGuest(Long id);

    ResponseEntity<GuestResponse> updateGuest(Long id, Guest guest);

    ResponseEntity<GuestResponse> createGuest(Guest guest);

}
