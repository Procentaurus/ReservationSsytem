package procentaurus.projects.ReservationSystem.StuffMember;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/stuffMembers/")
public class StuffMemberController implements StuffMemberControllerInterface {

    private final StuffMemberService stuffMemberService;

    @Autowired
    public StuffMemberController(StuffMemberService stuffMemberService) {
        this.stuffMemberService = stuffMemberService;
    }

    @Override
    @GetMapping(path = "{id}/", produces = "application/json")
    public ResponseEntity<?> findSingleStuffMember(@PathVariable Long id) {
        Optional<StuffMember> found =  stuffMemberService.findSingleStuffMember(id);
        if(found.isPresent()) return ResponseEntity.ok(found);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id.");
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<StuffMember>> findStuffMembers(Map<String, String> params) {
        List<StuffMember> found = stuffMemberService.findStuffMembers(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "{id}/")
    public ResponseEntity<?> deleteStuffMember(@PathVariable Long id) {
        boolean success = stuffMemberService.deleteStuffMember(id);
        if(success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id.");
    }

    @Override
    @PutMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateStuffMember(@PathVariable Long id, @RequestBody StuffMemberUpdateDto stuffMember) {
        Optional<StuffMember> updated = stuffMemberService.updateStuffMember(id, stuffMember);

        if(updated.isPresent()) return ResponseEntity.ok(updated);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id or wrong params.");
    }

    @Override
    @PostMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createStuffMember(@Valid @RequestBody StuffMemberCreationDto stuffMember) {
        Optional<StuffMember> created = stuffMemberService.createStuffMember(stuffMember);

        if(created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(created);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}