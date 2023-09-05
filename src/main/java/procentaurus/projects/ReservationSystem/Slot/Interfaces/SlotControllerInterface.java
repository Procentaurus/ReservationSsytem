package procentaurus.projects.ReservationSystem.Slot.Interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreateDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotUpdateDto;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.util.List;
import java.util.Map;

public interface SlotControllerInterface {

    ResponseEntity<?> findSingleSlot(Long id);

    ResponseEntity<List<Slot>> findSlots(Map<String, String> params);

    ResponseEntity<?> deleteSlot(Long id);

    ResponseEntity<?> updateSlot(Long id, SlotUpdateDto slotUpdateDto, UserDetails userDetails);

    ResponseEntity<?> createSlot(SlotCreateDto slot);
}
