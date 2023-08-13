package procentaurus.projects.ReservationSystem.Guest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Guest.Dtos.GuestCustomerDto;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.GuestControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/guests")
public class GuestController implements GuestControllerInterface {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @Override
    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestResponse> getSingleGuest(@PathVariable Long id) {

        Optional<Guest> guest = guestService.findSingleGuest(id);

        if (guest.isPresent()){
            GuestResponse guestResponse = new GuestResponse(new GuestCustomerDto(guest.get()));
            return ResponseEntity.status(HttpStatus.OK).body(guestResponse);
        }else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("There is no guest of provided id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(guestResponse);
        }
    }

    @Override
    @GetMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Guest>> getGuests(@RequestParam Map<String, String> params) {

        List<Guest> guests = !params.isEmpty() ? guestService.findGuests(null) : guestService.findGuests(params);
        return ResponseEntity.status(HttpStatus.OK).body(guests);
    }

    @Override
    @DeleteMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<GuestResponse> deleteGuest(@PathVariable Long id) {

        boolean successfulDeletion = guestService.deleteGuest(id);
        if(successfulDeletion) return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new GuestResponse(null, "The deletion was successful."));
        else return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new GuestResponse(null, "The deletion was not successful. Object of provided id does not exist in dataBase."));
    }

    @Override
    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestResponse> updateGuest(@PathVariable Long id, @RequestBody Guest guest) {

        Optional<Guest> updatedGuest = guestService.updateGuest(id, guest);

        if (updatedGuest.isPresent()){
            GuestResponse guestResponse = new GuestResponse(new GuestCustomerDto(updatedGuest.get()));
            return ResponseEntity.status(200).body(guestResponse);
        }else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("There is no guest of provided id.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(guestResponse);
        }
    }

    @Override
    @PostMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<GuestResponse> createGuest(@RequestBody Guest guest) {


        Optional<Guest> savedGuest = guestService.createGuest(guest);
        if (savedGuest.isPresent()){
            GuestResponse guestResponse = new GuestResponse(new GuestCustomerDto(savedGuest.get()));
            return ResponseEntity.status(204).body(guestResponse);
        }else{
            GuestResponse guestResponse = new GuestResponse(null);
            guestResponse.setMessage("Cannot create user with such a data.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(guestResponse);
        }

    }
}
