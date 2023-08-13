package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GuestServiceInterface {
    Optional<Guest> findSingleGuest(Long id);
    List<Guest> findGuests(Map<String, String> params);
    boolean deleteGuest(Long id);
    Optional<Guest> updateGuest(Long id, Guest guest);
    Optional<Guest> createGuest(Guest guest);
}
