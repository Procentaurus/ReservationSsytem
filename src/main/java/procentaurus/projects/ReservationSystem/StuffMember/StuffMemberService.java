package procentaurus.projects.ReservationSystem.StuffMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;

import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberRepository;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberServiceInterface;
import procentaurus.projects.ReservationSystem.MyUser.MyUser;

import java.time.LocalDate;
import java.util.*;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.isFilteringByDatePossible;
import static procentaurus.projects.ReservationSystem.StuffMember.StuffMemberFilter.*;
import static procentaurus.projects.ReservationSystem.MyUser.MyUserFilter.*;


@Service
public class StuffMemberService implements StuffMemberServiceInterface {

    private final StuffMemberRepository stuffMemberRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public StuffMemberService(StuffMemberRepository stuffMemberRepository, PasswordEncoder passwordEncoder) {
        this.stuffMemberRepository = stuffMemberRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<StuffMember> findSingleStuffMember(Long id) {
        return stuffMemberRepository.findById(id);
    }

    public UserDetails findSingleStuffMember(String email) throws DataBaseErrorException {

        if(stuffMemberRepository.existsByEmail(email)){
            Optional<StuffMember> found = stuffMemberRepository.findByEmail(email);
            if(found.isPresent()){
                StuffMember temp = found.get();
                return new User(temp.getEmail(), temp.getPassword(), new LinkedList<>());
            }else throw new DataBaseErrorException();
        }else throw new UsernameNotFoundException("User with email: "+email+" does not exist");
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
                    all = filterByRole(all, Role.valueOf(params.get("role")));

            if (params.containsKey("firstName"))
                all = filterByFirstName(all.stream().map(x -> (MyUser)x).toList(), params.get("firstName"))
                        .stream().map(y -> (StuffMember) y).toList();

            if (params.containsKey("lastName"))
                all = filterByLastName(all.stream().map(x -> (MyUser)x).toList(), params.get("lastName"))
                        .stream().map(y -> (StuffMember) y).toList();

            if (params.containsKey("dateOfBirth"))
                if (isFilteringByDatePossible(params.get("dateOfBirth").substring(3)))
                    all = filterByDateOfBirth(all.stream().map(y -> (MyUser) y).toList(),
                            LocalDate.parse(params.get("dateOfBirth").substring(3)), params.get("dateOfBirth").substring(0, 2))
                            .stream().map(y -> (StuffMember) y).toList();

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

        Optional<StuffMember> toUpdate = stuffMemberRepository.findById(id);
        if(toUpdate.isPresent() && stuffMember != null && stuffMember.isValid()){

            String firstName = stuffMember.getFirstName();
            String lastName = stuffMember.getLastName();
            LocalDate dateOfBirth = stuffMember.getDateOfBirth();
            Integer phoneNumber = stuffMember.getPhoneNumber();
            String email = stuffMember.getEmail();
            Role role = stuffMember.getRole();


            try {
                if(firstName != null) toUpdate.get().setFirstName(firstName);
                if(lastName != null) toUpdate.get().setLastName(lastName);
                if(dateOfBirth != null) toUpdate.get().setDateOfBirth(dateOfBirth);
                if(phoneNumber != null) toUpdate.get().setPhoneNumber(phoneNumber);
                if(email != null) toUpdate.get().setEmail(email);
                if(role != null) toUpdate.get().setRole(role);

                stuffMemberRepository.save(toUpdate.get());
                return toUpdate;

            } catch (Exception ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

//    @Override
//    public Optional<StuffMember> createStuffMember(StuffMemberCreationDto stuffMember) throws UserAlreadyExistsException {
//
//        if (!stuffMember.getPassword().equals(stuffMember.getPasswordConfirmation()))
//            throw new IllegalArgumentException();
//
//        if(stuffMemberRepository.existsByEmail(stuffMember.getEmail()))
//            throw new UserAlreadyExistsException();
//
//        StuffMember newStuffMember = new StuffMember(
//                stuffMember.getFirstName(),
//                stuffMember.getLastName(),
//                stuffMember.getDateOfBirth(),
//                stuffMember.getPhoneNumber(),
//                stuffMember.getEmail(),
//                stuffMember.getRole(),
//                passwordEncoder.encode(stuffMember.getPassword()),
//                LocalDate.now()
//        );
//
//        StuffMember savedStuffMember = stuffMemberRepository.save(newStuffMember);
//        return Optional.of(savedStuffMember);
//    }

}