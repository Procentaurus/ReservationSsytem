package procentaurus.projects.ReservationSystem.Slot.Interfaces;

import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreationDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotUpdateDto;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SlotServiceInterface{

    Optional<Slot> findSingleSlot(Long id);

    List<Slot> findSlots(Map<String, String> params);

    boolean deleteSlot(Long id);

    Optional<Slot> updateSlot(Long id, SlotUpdateDto slotUpdateDto);

    List<Slot> createSlot(SlotCreationDto slot) throws DataBaseErrorException;
}
