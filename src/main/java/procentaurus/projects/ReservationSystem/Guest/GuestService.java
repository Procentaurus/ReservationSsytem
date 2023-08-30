package procentaurus.projects.ReservationSystem.Guest;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.*;
import procentaurus.projects.ReservationSystem.User.User;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static procentaurus.projects.ReservationSystem.Guest.GuestFilter.filterBySignedForNewsletter;
import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.isFilteringByDatePossible;
import static procentaurus.projects.ReservationSystem.User.UserFilter.*;

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

        List<Guest> all = guestRepository.findAll();
        if(params != null) {

            if (params.containsKey("signedForNewsletter"))
                all = filterBySignedForNewsletter(all, Boolean.parseBoolean(params.get("signedForNewsletter")));

            if (params.containsKey("firstName"))
                all = filterByFirstName(all.stream().map(x -> (User)x).toList(), params.get("firstName"))
                        .stream().map(y -> (Guest) y).toList();

            if (params.containsKey("lastName"))
                all = filterByLastName(all.stream().map(x -> (User)x).toList(), params.get("lastName"))
                        .stream().map(y -> (Guest) y).toList();

            if (params.containsKey("dateOfBirth"))
                if (isFilteringByDatePossible(params.get("dateOfBirth").substring(3)))
                    all = filterByDateOfBirth(all.stream().map(y -> (User) y).toList(),
                            LocalDate.parse(params.get("dateOfBirth").substring(3)), params.get("dateOfBirth").substring(0, 2))
                            .stream().map(y -> (Guest) y).toList();

        }
        return all;
    }

    @Override
    @Transactional
    public boolean deleteGuest(Long id) {
        if(guestRepository.existsById(id)){
            guestRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteGuest(String email) {
        if(guestRepository.existsByEmail(email)){
            guestRepository.deleteByEmail(email);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<Guest> updateGuest(Long id, @Valid GuestBasicDto guest) {

        Optional<Guest> toUpdate = guestRepository.findById(id);

        if(toUpdate.isPresent()) {
            if (guest.getPhoneNumber() != 0) toUpdate.get().setPhoneNumber(guest.getPhoneNumber());
            if (guest.getFirstName() != null) toUpdate.get().setFirstName(guest.getFirstName());
            if (guest.getDateOfBirth() != null) toUpdate.get().setDateOfBirth(guest.getDateOfBirth());
            if (guest.getLastName() != null) toUpdate.get().setLastName(guest.getLastName());
            if(guest.getSignedForNewsletter() != null) toUpdate.get().setSignedForNewsletter(guest.getSignedForNewsletter());

            guestRepository.save(toUpdate.get());
            return toUpdate;
        }else{
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Guest> updateGuest(String email, @Valid GuestBasicDto guest) {

        Optional<Guest> toUpdate = guestRepository.findByEmail(email);

        if(toUpdate.isPresent()) {
            Guest existingGuest = toUpdate.get();
            try {
                if (guest.getPhoneNumber() != 0) existingGuest.setPhoneNumber(guest.getPhoneNumber());
                if (guest.getFirstName() != null) existingGuest.setFirstName(guest.getFirstName());
                if (guest.getDateOfBirth() != null) existingGuest.setDateOfBirth(guest.getDateOfBirth());
                if (guest.getLastName() != null) existingGuest.setLastName(guest.getLastName());
                if (guest.getSignedForNewsletter() != null) existingGuest.setSignedForNewsletter(guest.getSignedForNewsletter());

                guestRepository.save(existingGuest);
                return toUpdate;

            }catch(Exception e){
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
