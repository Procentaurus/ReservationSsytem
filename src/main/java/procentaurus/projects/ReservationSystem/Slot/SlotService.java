package procentaurus.projects.ReservationSystem.Slot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Room.Room;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotRepository;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotServiceInterface;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static procentaurus.projects.ReservationSystem.Room.RoomFilter.*;
import static procentaurus.projects.ReservationSystem.Room.RoomFilter.filterByRoomType;
import static procentaurus.projects.ReservationSystem.Slot.SlotFilter.*;
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
                if(isFilteringByDatePossible(params.get("date")))
                    result = filterByDate(all, LocalDate.parse("date"));

            if(params.containsKey("reservationId"))
                if(isFilteringByLongPossible(params.get("reservationId")))
                    result = filterByReservationId(all, Long.parseLong(params.get("hasLakeView")));

            if(params.containsKey("roomNumber"))
                if(isFilteringByIntPossible(params.get("roomNumber")))
                    result = filterByRoomNumber(all, Integer.parseInt(params.get("roomNumber")));
            else if(params.containsKey("conferenceRoomNumber"))
                if(isFilteringByIntPossible(params.get("conferenceRoomNumber")))
                    result = filterByConferenceRoomNumber(all, Integer.parseInt(params.get("conferenceRoomNumber")));
            else if((params.containsKey("parkingPlace")))
                if(isFilteringByIntPossible(params.get("parkingPlaceNumber")))
                    result = filterByParkingPlaceNumber(all, Integer.parseInt(params.get("parkingPlaceNumber")));

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
    public Optional<Slot> createSlot(Space space, LocalDate date) {
        try {
            Slot slot = new Slot(space, date);
            Slot created = slotRepository.save(slot);
            return Optional.of(created);

        }catch(IllegalArgumentException ex){
            return Optional.empty();
        }
    }
}
