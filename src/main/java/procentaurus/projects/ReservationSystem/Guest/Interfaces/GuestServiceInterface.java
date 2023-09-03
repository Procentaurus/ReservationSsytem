package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GuestServiceInterface {
    Optional<Guest> findSingleGuest(Long id);
    Optional<Guest> findSingleGuest(String email);

    List<Guest> findGuests(Map<String, String> params);

    boolean deleteGuest(Long id);
    boolean deleteGuest(String email);

    Optional<Guest> updateGuest(Long id, GuestBasicDto guest);
    Optional<Guest> updateGuest(String email, GuestBasicDto guest);

    Optional<Guest> createGuest(GuestBasicDto guest) throws UserAlreadyExistsException;
}
