package procentaurus.projects.ReservationSystem.Guest;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestAdminDto;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestBasicDto;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.GuestControllerInterface;
import procentaurus.projects.ReservationSystem.Miscellaneous.AuthorityChecker;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/guests/")
public class GuestController implements GuestControllerInterface {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    @GetMapping(path = "{id}/", produces = "application/json")
    public ResponseEntity<GuestResponse> getSingleGuest(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Guest> guest = guestService.findSingleGuest(id);

        if (guest.isPresent()){

            GuestResponse guestResponse;
            AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);

            if(authorityChecker.hasAdminAuthority())
                guestResponse = new GuestResponse(new GuestAdminDto(guest.get()));
            else
                guestResponse = new GuestResponse(new GuestBasicDto(guest.get()));

            return ResponseEntity.status(HttpStatus.OK).body(guestResponse);
        }
        else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("There is no guest of provided id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(guestResponse);
        }
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<? extends GuestBasicDto>> getGuests(@RequestParam Map<String, String> params, @AuthenticationPrincipal UserDetails userDetails) {

        List<Guest> guests = !params.isEmpty() ? guestService.findGuests(null) : guestService.findGuests(params);

        AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
        if(authorityChecker.hasAdminAuthority())
            return ResponseEntity.status(HttpStatus.OK).body(guests.stream().map(GuestAdminDto::new).toList());
        else
            return ResponseEntity.status(HttpStatus.OK).body(guests.stream().map(GuestBasicDto::new).toList());
    }

    @Override
    @DeleteMapping(path = "{id}/")
    public ResponseEntity<GuestResponse> deleteGuest(@PathVariable Long id) {

        boolean successfulDeletion = guestService.deleteGuest(id);
        if(successfulDeletion) return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new GuestResponse(null, "The deletion was successful."));
        else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new GuestResponse(null, "The deletion was not successful. Guest of provided id does not exist."));
    }

    @Override
    @PutMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestResponse> updateGuest(@PathVariable Long id, @Valid @RequestBody GuestBasicDto guest) {

        Optional<Guest> updatedGuest;
        try {
            updatedGuest = guestService.updateGuest(id, guest);
        }catch(IllegalArgumentException e){
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(guestResponse);
        }

        if (updatedGuest.isPresent()){
            GuestResponse guestResponse = new GuestResponse(new GuestBasicDto(updatedGuest.get()));
            return ResponseEntity.status(200).body(guestResponse);
        }else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("There is no guest of provided id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(guestResponse);
        }
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestResponse> createGuest(@Valid  @RequestBody GuestBasicDto guest) {

        Optional<Guest> savedGuest;
        try {
            savedGuest = guestService.createGuest(guest);
        }catch (UserAlreadyExistsException e){
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("Cannot create user with such a data.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(guestResponse);
        }
        if (savedGuest.isPresent()){
            GuestResponse guestResponse = new GuestResponse(new GuestBasicDto(savedGuest.get()));
            return ResponseEntity.status(HttpStatus.CREATED).body(guestResponse);
        }else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("Server error occured. Contact admin.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(guestResponse);
        }

    }
}
