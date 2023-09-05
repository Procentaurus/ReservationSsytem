package procentaurus.projects.ReservationSystem.Configuration.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import procentaurus.projects.ReservationSystem.Configuration.Auth.Jwt.JwtAuthenticationService;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;

    @PostMapping(path = "register/")
    public ResponseEntity<?> createStuffMember(@RequestBody RegisterRequest request)
    {
        try {
            return ResponseEntity.ok(jwtAuthenticationService.register(request));
        } catch (UserAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataBaseErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred. Contact admin.");
        }
    }

    @PostMapping(path = "authenticate/")
    public ResponseEntity<?> authenticateStuffMember(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(jwtAuthenticationService.authenticate(request));
        }catch(NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}