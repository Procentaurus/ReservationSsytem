package procentaurus.projects.ReservationSystem.Slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotLightDto;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots")
public class SlotController implements SlotControllerInterface {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findSingleSlot(Long id) {
        Optional<Slot> found = slotService.findSingleSlot(id);
        if (found.isPresent()) return ResponseEntity.ok(new SlotLightDto(found.get()));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No slot of provided id.");
    }

    @Override
    @GetMapping(path = "/")
    public ResponseEntity<List<Slot>> findSlots(Map<String, String> params) {
        List<Slot> found = slotService.findSlots(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSlot(Long id) {
        boolean success = slotService.deleteSlot(id);
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No slot of provided number.");
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateSlot(Long id, Map<String, String> params) {
        Optional<Slot> updated = slotService.updateSlot(id, params);

        if (updated.isPresent()) return ResponseEntity.ok(new SlotLightDto(updated.get()));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No parking place of provided number or wrong params.");
    }

    @Override
    @PostMapping(path = "/{id}")
    public ResponseEntity<?> createSlot(Slot slot) {
        Optional<Slot> created = slotService.createSlot(slot);

        if (created.isPresent()) return ResponseEntity.status(HttpStatus.CREATED).body(new SlotLightDto(created.get()));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong data passed.");
    }
}
