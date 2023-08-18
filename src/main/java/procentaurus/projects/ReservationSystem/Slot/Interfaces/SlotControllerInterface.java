package procentaurus.projects.ReservationSystem.Slot.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreationDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotUpdateDto;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SlotControllerInterface {

    ResponseEntity<?> findSingleSlot(Long id);

    ResponseEntity<List<Slot>> findSlots(Map<String, String> params);

    ResponseEntity<?> deleteSlot(Long id);

    ResponseEntity<?> updateSlot(Long id, SlotUpdateDto slotUpdateDto);

    ResponseEntity<?> createSlot(SlotCreationDto slot);
}
