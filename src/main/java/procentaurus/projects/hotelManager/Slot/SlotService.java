package procentaurus.projects.hotelManager.Slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import procentaurus.projects.hotelManager.Slot.Interfaces.SlotRepository;
import procentaurus.projects.hotelManager.Slot.Interfaces.SlotServiceInterface;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class SlotService implements SlotServiceInterface {

    private final SlotRepository slotRepository;

    @Autowired
    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Optional<Slot> findSingleSlot(Long id) {
        return slotRepository.findById(id);
    }

    @Override
    public List<Slot> findSlots(Map<String, String> params) {
        return null;
    }

    @Override
    public boolean deleteSlot(Long id) {
        if(slotRepository.existsById(id)){
            slotRepository.deleteById(id);
            return true;
        }else return false;
    }

    @Override
    public Optional<Slot> updateSlot(Long id, Map<String, String> params) {
        return Optional.empty();
    }

    @Override
    public Optional<Slot> createSlot(Slot slot) {
        return Optional.empty();
    }
}
