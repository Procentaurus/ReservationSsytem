package procentaurus.projects.hotelManager.Slot.Interfaces;

import org.springframework.http.ResponseEntity;
import procentaurus.projects.hotelManager.ParkingPlace.ParkingPlace;
import procentaurus.projects.hotelManager.Slot.Slot;

import java.util.List;
import java.util.Map;

public interface SlotControllerInterface {

    ResponseEntity<?> findSingleSlot(Long id);

    ResponseEntity<List<Slot>> findSlots(Map<String, String> params);

    ResponseEntity<?> deleteSlot(Long id);

    ResponseEntity<?> updateSlot(Long id, Map<String, String> params);

    ResponseEntity<?> createSlot(Slot slot);
}
