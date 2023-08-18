package procentaurus.projects.ReservationSystem.Slot;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreationDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotMediumDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotUpdateDto;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotControllerInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/slots/")
public class SlotController implements SlotControllerInterface {

    private final SlotService slotService;

    @Autowired
    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @Override
    @GetMapping(path = "{id}/", produces = "application/json")
    public ResponseEntity<?> findSingleSlot(@PathVariable Long id) {
        Optional<Slot> found = slotService.findSingleSlot(id);
        if (found.isPresent()) return ResponseEntity.ok(new SlotMediumDto(found.get()));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No slot of provided id.");
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Slot>> findSlots(@RequestParam Map<String, String> params) {
        List<Slot> found = slotService.findSlots(params);
        return ResponseEntity.ok(found);
    }

    @Override
    @DeleteMapping(path = "{id}/", produces = "application/json")
    public ResponseEntity<?> deleteSlot(@PathVariable Long id) {
        boolean success = slotService.deleteSlot(id);
        if (success) return ResponseEntity.noContent().build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No slot of provided number.");
    }

    @Override
    @PutMapping(path = "{id}/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateSlot(@PathVariable Long id, @RequestBody SlotUpdateDto slotUpdateDto) {
        Optional<Slot> updated = slotService.updateSlot(id, slotUpdateDto);

        if (updated.isPresent()) return ResponseEntity.ok(new SlotMediumDto(updated.get()));
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No parking place of provided number or wrong params.");
    }

    @Override
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createSlot(@Valid @RequestBody SlotCreationDto slot){

        List<Slot> created;
        try {
            created = slotService.createSlot(slot);
        }catch (DataBaseErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some error occurred. Please contact the service administrator.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        List<SlotMediumDto> toReturn = created.stream().map(SlotMediumDto::new).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(toReturn);
    }
}
