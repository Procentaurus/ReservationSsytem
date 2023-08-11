package procentaurus.projects.ReservationSystem.Slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Room.Room;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotRepository;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotServiceInterface;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static procentaurus.projects.ReservationSystem.Room.RoomFilter.*;
import static procentaurus.projects.ReservationSystem.Room.RoomFilter.filterByRoomType;
import static procentaurus.projects.ReservationSystem.Slot.SlotFilter.filterByStatus;
import static procentaurus.projects.ReservationSystem.Slot.SlotFilter.isFilteringByStatusPossible;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.*;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.filterByPrice;


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

        List<Slot> all = slotRepository.findAll(), result = null;

        if(params != null) {

            if (params.containsKey("status"))
                if(isFilteringByStatusPossible(params.get("status")))
                    result = filterByStatus(all, Slot.Status.valueOf(params.get("status")));

            if (params.containsKey("date"))
                if(isFilteringByPricePossible(params.get("price")))
                    result = filterByPrice(all.stream().map(x ->(Space) x).toList(),
                            Float.parseFloat(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            Room.class);

            if(params.containsKey("reservationId")) result = filterByHasLakeView(all, params.get("hasLakeView"));

            if(params.containsKey("roomId")) result = filterByIsSmokingAllowed(all, params.get("hasLakeView"));

            if(params.containsKey("roomType"))
                if(isFilteringByRoomTypePossible(params.get("roomType")))
                    result = filterByRoomType(all, Room.RoomType.valueOf(params.get("roomType")));

        }
        return result;
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

        Optional<Slot> toUpdate = slotRepository.findById(id);
        if(toUpdate.isPresent()){

            Slot.Status status;
            if (params.containsKey("roomType")) {
                try {
                    status = Slot.Status.valueOf(params.get("status").toUpperCase());
                } catch (IllegalArgumentException e) {
                    status = null;
                }
            } else status = null;

            try {
                if(status != null) toUpdate.get().setStatus(status);

                slotRepository.save(toUpdate.get());
                return toUpdate;

            } catch (DataAccessException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Slot> createSlot(Slot slot) {
        return Optional.empty();
    }
}
