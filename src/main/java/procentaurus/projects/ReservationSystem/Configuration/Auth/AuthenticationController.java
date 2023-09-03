package procentaurus.projects.ReservationSystem.Configuration.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import procentaurus.projects.ReservationSystem.Configuration.Auth.Jwt.JwtAuthenticationService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtAuthenticationService jwtAuthenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> generateAuthenticationToken(@RequestBody RegisterRequest request)
            throws Exception {
        return ResponseEntity.ok(jwtAuthenticationService.register(request));
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponse> generateAuthenticationToken(@RequestBody AuthenticationRequest request)
            throws Exception {
        return ResponseEntity.ok(jwtAuthenticationService.authenticate(request));
    }

}