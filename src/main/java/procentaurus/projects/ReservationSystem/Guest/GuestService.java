package procentaurus.projects.ReservationSystem.Guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.GuestRepository;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.GuestServiceInterface;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GuestService implements GuestServiceInterface {

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
    public Optional<Guest> findSingleGuest(String email) {
        return guestRepository.findByEmail(email);
    }

    @Override
    public List<Guest> findGuests(Map<String, String> params) {
        return null;
    }

    @Override
    public boolean deleteGuest(Long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteGuest(String email) {
        if(guestRepository.existsByEmail(email)){
            guestRepository.deleteByEmail(email);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Optional<Guest> updateGuest(Long id, GuestBasicDto guest) {

        Optional<Guest> toUpdate = guestRepository.findById(id);

        if(toUpdate.isPresent()) {
            if (guest.getPhoneNumber() != 0) toUpdate.get().setPhoneNumber(guest.getPhoneNumber());
            if (guest.getFirstName() != null) toUpdate.get().setFirstName(guest.getFirstName());
            if (guest.getDateOfBirth() != null) toUpdate.get().setDateOfBirth(guest.getDateOfBirth());
            if (guest.getLastName() != null) toUpdate.get().setLastName(guest.getLastName());
            if (guest.getEmail() != null) toUpdate.get().setEmail(guest.getEmail());

            guestRepository.save(toUpdate.get());
            return toUpdate;
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Guest> updateGuest(String email, GuestBasicDto guest) {

        Optional<Guest> toUpdate = guestRepository.findByEmail(email);

        if(toUpdate.isPresent()) {
            Guest existingGuest = toUpdate.get();
            try {
                if (guest.getPhoneNumber() != 0) existingGuest.setPhoneNumber(guest.getPhoneNumber());
                if (guest.getFirstName() != null) existingGuest.setFirstName(guest.getFirstName());
                if (guest.getDateOfBirth() != null) existingGuest.setDateOfBirth(guest.getDateOfBirth());
                if (guest.getLastName() != null) existingGuest.setLastName(guest.getLastName());
                if (guest.getEmail() != null) existingGuest.setEmail(guest.getEmail());
                if(guest.getSignedForNewsletter() != null) existingGuest.setSignedForNewsletter(guest.getSignedForNewsletter());

                guestRepository.save(existingGuest);
                return toUpdate;

            }catch(DataAccessException e){
                return Optional.empty();
            }
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Guest> createGuest(Guest guest) {
        try {
            Guest created = guestRepository.save(guest);
            return Optional.of(created);

        }catch(IllegalArgumentException ex){
            return Optional.empty();
        }
    }
}
