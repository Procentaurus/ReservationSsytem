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
@RequestMapping("/api/stuffMembers")
public class StuffMemberController implements StuffMemberControllerInterface {

    private final StuffMemberService stuffMemberService;

    @Autowired
    public StuffMemberController(StuffMemberService stuffMemberService) {
        this.stuffMemberService = stuffMemberService;
    }

    @Override
    public ResponseEntity<?> findSingleStuffMember(@PathVariable Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<StuffMember>> findStuffMembers(Map<String, String> params) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteStuffMember(@PathVariable Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateStuffMember(@PathVariable Long id, @RequestBody StuffMemberUpdateDto room) {
        return null;
    }

    @Override
    public ResponseEntity<?> createStuffMember(@Valid @RequestBody StuffMemberCreationDto stuffMember) {
        return null;
    }
}
