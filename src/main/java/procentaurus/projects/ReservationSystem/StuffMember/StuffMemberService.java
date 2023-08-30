package procentaurus.projects.ReservationSystem.StuffMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;

import procentaurus.projects.ReservationSystem.Room.Room;
import procentaurus.projects.ReservationSystem.Space.Space;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberRepository;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberServiceInterface;
import procentaurus.projects.ReservationSystem.User.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.isFilteringByDatePossible;
import static procentaurus.projects.ReservationSystem.Room.RoomFilter.*;
import static procentaurus.projects.ReservationSystem.Room.RoomFilter.filterByRoomType;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.*;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.filterByPrice;
import static procentaurus.projects.ReservationSystem.StuffMember.StuffMemberFilter.*;
import static procentaurus.projects.ReservationSystem.User.UserFilter.*;


@Service
public class StuffMemberService implements StuffMemberServiceInterface {

    private final StuffMemberRepository stuffMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StuffMemberService(StuffMemberRepository stuffMemberRepository, PasswordEncoder passwordEncoder) {
        this.stuffMemberRepository = stuffMemberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<StuffMember> findSingleStuffMember(Long id) {
        return stuffMemberRepository.findById(id);
    }

    @Override
    public List<StuffMember> findStuffMembers(Map<String, String> params) {

        List<StuffMember> all = stuffMemberRepository.findAll();
        if(params != null) {

            if (params.containsKey("employedFrom"))
                if (isFilteringByDatePossible(params.get("employedFrom").substring(3)))
                    all = filterByDateEmployedFrom(all,
                            LocalDate.parse(params.get("employedFrom").substring(3)),
                            params.get("employedFrom").substring(0, 2));

            if (params.containsKey("role"))
                if (isFilteringByRolePossible(params.get("role").toUpperCase()))
                    all = filterByRole(all, StuffMember.Role.valueOf(params.get("role")));

            if (params.containsKey("firstName"))
                all = filterByFirstName(all.stream().map(x -> (User)x).toList(), params.get("firstName"))
                        .stream().map(y -> (StuffMember) y).toList();

            if (params.containsKey("lastName"))
                all = filterByLastName(all.stream().map(x -> (User)x).toList(), params.get("lastName"))
                        .stream().map(y -> (StuffMember) y).toList();

            if (params.containsKey("dateOfBirth"))
                if (isFilteringByDatePossible(params.get("dateOfBirth").substring(3)))
                    all = filterByDateOfBirth(all.stream().map(y -> (User) y).toList(),
                            LocalDate.parse(params.get("dateOfBirth").substring(3)), params.get("dateOfBirth").substring(0, 2))
                            .stream().map(y -> (StuffMember) y).toList();;

        }
        return all;
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
    public Optional<StuffMember> createStuffMember(StuffMemberCreationDto stuffMember) throws UserAlreadyExistsException {

        if (!stuffMember.getPassword().equals(stuffMember.getPasswordConfirmation()))
            throw new IllegalArgumentException();

        if(stuffMemberRepository.existsByEmail(stuffMember.getEmail()))
            throw new UserAlreadyExistsException();

        StuffMember newStuffMember = new StuffMember(
                stuffMember.getFirstName(),
                stuffMember.getLastName(),
                stuffMember.getDateOfBirth(),
                stuffMember.getPhoneNumber(),
                stuffMember.getEmail(),
                stuffMember.getRole(),
                passwordEncoder.encode(stuffMember.getPassword()),
                LocalDate.now()
        );

        StuffMember savedStuffMember = stuffMemberRepository.save(newStuffMember);
        return Optional.of(savedStuffMember);
    }

}

//    List<Room> all = roomRepository.findAll();
//
//        if(params != null) {
//
//                if (params.containsKey("capacity"))
//                if (isFilteringByCapacityPossible(params.get("price")))
//                all = filterByCapacity(all.stream().map(x -> (Space) x).toList(),
//                Integer.parseInt(params.get("price").substring(2)),    // value passed
//                params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
//                Room.class);
//
//        if (params.containsKey("price"))
//        if (isFilteringByPricePossible(params.get("price")))
//        all = filterByPrice(all.stream().map(x -> (Space) x).toList(),
//        Float.parseFloat(params.get("price").substring(2)),    // value passed
//        params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
//        Room.class);
//
//        if (params.containsKey("hasLakeView")) all = filterByHasLakeView(all, params.get("hasLakeView"));
//
//        if (params.containsKey("isSmokingAllowed")) all = filterByIsSmokingAllowed(all, params.get("isSmokingAllowed"));
//
//        if (params.containsKey("roomType"))
//        if (isFilteringByRoomTypePossible(params.get("roomType").toUpperCase()))
//        all = filterByRoomType(all, Room.RoomType.valueOf(params.get("roomType").toUpperCase()));
//
//        }
//        return all;

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
