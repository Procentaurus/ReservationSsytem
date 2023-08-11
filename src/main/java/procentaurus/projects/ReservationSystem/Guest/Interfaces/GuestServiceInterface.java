package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.util.MultiValueMap;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestServiceInterface {
    Optional<Guest> findSingleGuest(Long id);
    List<Guest> findGuestsByFilter(MultiValueMap<String, String> params);
    List<Guest> findAllGuests();
    boolean deleteGuest(Long id);
    Optional<Guest> updateGuest(Long id, Guest guest);
    Optional<Guest> createGuest(Guest guest);
}
