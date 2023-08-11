package procentaurus.projects.hotelManager.Guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService implements GuestServiceInterface{

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public Optional<Guest> findSingleGuest(Long id) {
        return guestRepository.findById(id);
    }

    @Override
    public List<Guest> findGuestsByFilter(MultiValueMap<String, String> params) {
        return null;
    }

    @Override
    public List<Guest> findAllGuests() {
        return null;
    }

    @Override
    public boolean deleteGuest(Long id) {
        return false;
    }

    @Override
    public Optional<Guest> updateGuest(Long id, Guest guest) {
        return null;
    }

    @Override
    public Optional<Guest> createGuest(Guest guest) {
        return null;
    }
}
