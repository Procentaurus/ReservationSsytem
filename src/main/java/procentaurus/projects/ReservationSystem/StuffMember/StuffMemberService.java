package procentaurus.projects.ReservationSystem.StuffMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberRepository;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberServiceInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StuffMemberService implements StuffMemberServiceInterface {

    private final StuffMemberRepository stuffMemberRepository;

    @Autowired
    public StuffMemberService(StuffMemberRepository stuffMemberRepository) {
        this.stuffMemberRepository = stuffMemberRepository;
    }

    @Override
    public Optional<StuffMember> findSingleStuffMember(Long id) {
        return stuffMemberRepository.findById(id);
    }

    @Override
    public List<StuffMember> findStuffMembers(Map<String, String> params) {
        return null;
    }

    @Override
    @Transactional
    public boolean deleteStuffMember(Long id) {
        if(stuffMemberRepository.existsById(id)){
            stuffMemberRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional
    public Optional<StuffMember> updateStuffMember(Long id, StuffMemberUpdateDto stuffMember) {
        return Optional.empty();
    }

    @Override
    public Optional<StuffMember> createStuffMember(StuffMemberCreationDto stuffMember) {
        return Optional.empty();
    }
}

//
//    @Override
//    @Transactional
//    public Optional<Guest> updateGuest(Long id, @Valid GuestBasicDto guest) {
//
//        Optional<Guest> toUpdate = guestRepository.findById(id);
//
//        if(toUpdate.isPresent()) {
//            if (guest.getPhoneNumber() != 0) toUpdate.get().setPhoneNumber(guest.getPhoneNumber());
//            if (guest.getFirstName() != null) toUpdate.get().setFirstName(guest.getFirstName());
//            if (guest.getDateOfBirth() != null) toUpdate.get().setDateOfBirth(guest.getDateOfBirth());
//            if (guest.getLastName() != null) toUpdate.get().setLastName(guest.getLastName());
//            if (guest.getEmail() != null) toUpdate.get().setEmail(guest.getEmail());
//
//            guestRepository.save(toUpdate.get());
//            return toUpdate;
//        }else{
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    @Transactional
//    public Optional<Guest> updateGuest(String email, @Valid GuestBasicDto guest) {
//
//        Optional<Guest> toUpdate = guestRepository.findByEmail(email);
//
//        if(toUpdate.isPresent()) {
//            Guest existingGuest = toUpdate.get();
//            try {
//                if (guest.getPhoneNumber() != 0) existingGuest.setPhoneNumber(guest.getPhoneNumber());
//                if (guest.getFirstName() != null) existingGuest.setFirstName(guest.getFirstName());
//                if (guest.getDateOfBirth() != null) existingGuest.setDateOfBirth(guest.getDateOfBirth());
//                if (guest.getLastName() != null) existingGuest.setLastName(guest.getLastName());
//                if(guest.getSignedForNewsletter() != null) existingGuest.setSignedForNewsletter(guest.getSignedForNewsletter());
//
//                guestRepository.save(existingGuest);
//                return toUpdate;
//
//            }catch(Exception e){
//                return Optional.empty();
//            }
//        }else{
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<Guest> createGuest(Guest guest) {
//        try {
//            Guest created = guestRepository.save(guest);
//            return Optional.of(created);
//
//        }catch(IllegalArgumentException ex){
//            return Optional.empty();
//        }
//    }