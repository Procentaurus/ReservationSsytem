package procentaurus.projects.ReservationSystem.StuffMember;

import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import procentaurus.projects.ReservationSystem.Miscellaneous.AuthorityChecker;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberControllerInterface;

import java.util.Collection;
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
    public ResponseEntity<?> findSingleStuffMember(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        Optional<StuffMember> requestSender = stuffMemberService.findSingleStuffMember(userEmail);
        Optional<StuffMember> found = stuffMemberService.findSingleStuffMember(id);

        if(requestSender.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error. Contact admin.");
        if(found.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id.");

        boolean isUserChangingHisData = requestSender.get().equals(found.get());

        AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
        if(isUserChangingHisData || authorityChecker.hasAdminAuthority() || authorityChecker.hasManagerAuthority()){
            return ResponseEntity.ok(found);
        }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have necessary permissions to update that stuff memeber.");
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
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id.");
    }

    @Override
    @PutMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateStuffMember(
            @PathVariable Long id, @RequestBody StuffMemberUpdateDto stuffMember, @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = userDetails.getUsername();
        Optional<StuffMember> requestSender = stuffMemberService.findSingleStuffMember(userEmail);
        Optional<StuffMember> found = stuffMemberService.findSingleStuffMember(id);

        if(requestSender.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Authentication error. Contact admin.");
        if(found.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No stuff member of provided id.");

        boolean isUserChangingHisData = requestSender.get().equals(found.get());

        AuthorityChecker authorityChecker = new AuthorityChecker(userDetails);
        if(isUserChangingHisData || authorityChecker.hasAdminAuthority() || authorityChecker.hasManagerAuthority()){
            Optional<StuffMember> updated = stuffMemberService.updateStuffMember(id, stuffMember);
            if (updated.isPresent()) return ResponseEntity.ok(updated);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No stuff member of provided id or wrong params.");
        }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have necessary permissions to update that stuff member.");

    }
}