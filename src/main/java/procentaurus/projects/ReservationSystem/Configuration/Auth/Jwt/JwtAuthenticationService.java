package procentaurus.projects.ReservationSystem.Configuration.Auth.Jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Configuration.Auth.AuthenticationRequest;
import procentaurus.projects.ReservationSystem.Configuration.Auth.AuthenticationResponse;
import procentaurus.projects.ReservationSystem.Configuration.Auth.RegisterRequest;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberRepository;
import procentaurus.projects.ReservationSystem.StuffMember.StuffMember;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final StuffMemberRepository stuffMemberRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException, DataBaseErrorException {

        var stuffMember = request.getStuffMember();
        if (!stuffMember.getPassword().equals(stuffMember.getPasswordConfirmation()))
            throw new IllegalArgumentException("Password must be the same.");

        if(stuffMemberRepository.existsByEmail(stuffMember.getEmail()))
            throw new UserAlreadyExistsException("email");

        if(stuffMemberRepository.existsByPhoneNumber(stuffMember.getPhoneNumber()))
            throw new UserAlreadyExistsException("phone");

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

        StuffMember savedStuffMember = null;
        try {
            savedStuffMember = stuffMemberRepository.save(newStuffMember);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        var jwtToken = jwtService.generateToken(savedStuffMember);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws NoSuchElementException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = stuffMemberRepository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
