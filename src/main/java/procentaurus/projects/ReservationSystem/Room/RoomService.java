package procentaurus.projects.ReservationSystem.Room;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Exceptions.NonExistingRoomException;
import procentaurus.projects.ReservationSystem.Room.Interfaces.RoomRepository;
import procentaurus.projects.ReservationSystem.Room.Interfaces.RoomServiceInterface;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotRepository;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.checkIfDateIsInPeriod;
import static procentaurus.projects.ReservationSystem.Room.RoomFilter.*;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.*;
import static procentaurus.projects.ReservationSystem.Space.SpaceFilter.isFilteringByPricePossible;

@Service
public class RoomService implements RoomServiceInterface {

    private final RoomRepository roomRepository;
    private final SlotRepository slotRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, SlotRepository slotRepository) {
        this.roomRepository = roomRepository;
        this.slotRepository = slotRepository;
    }

    @Override
    public Optional<Room> findSingleRoom(int number) {
        return roomRepository.findByNumber(number);
    }

    @Override
    public List<Room> findRooms(Map<String, String> params) {
        List<Room> all = roomRepository.findAll(), result = null;

        if(params != null) {

            if (params.containsKey("capacity"))
                if(isFilteringByCapacityPossible(params.get("price")))
                    result = filterByCapacity(all.stream().map(x ->(Space) x).toList(),
                            Integer.parseInt(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            Room.class);

            if (params.containsKey("price"))
                if(isFilteringByPricePossible(params.get("price")))
                    result = filterByPrice(all.stream().map(x ->(Space) x).toList(),
                            Float.parseFloat(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            Room.class);

            if(params.containsKey("hasLakeView")) result = filterByHasLakeView(all, params.get("hasLakeView"));

            if(params.containsKey("isSmokingAllowed")) result = filterByIsSmokingAllowed(all, params.get("hasLakeView"));

            if(params.containsKey("roomType"))
                if(isFilteringByRoomTypePossible(params.get("roomType")))
                    result = filterByRoomType(all, Room.RoomType.valueOf(params.get("roomType")));

            return result;
        }else
            return all;
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate startDate, short numberOfDays, Room.RoomType standard, boolean viewForLake, boolean forSmokingPeople)
            throws NonExistingRoomException {

        ArrayList<Room> toReturn = new ArrayList<>();
        List<Slot> data = slotRepository.findByRoomIsNotNull();

        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream().collect(Collectors.groupingBy(slot -> slot.getRoom().getNumber()));

        for (Map.Entry<Integer, List<Slot>> entry : slotsByRoomId.entrySet()) {

            List<Slot> slotsInChosenPeriod = entry.getValue().stream().
                    filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays)).toList();

            boolean success = true;
            for (Slot slot : slotsInChosenPeriod) {
                if(slot.getRoom().getIsSmokingAllowed() != forSmokingPeople) success = false;
                if(slot.getRoom().getHasLakeView() == viewForLake) success = false;
                if(slot.getRoom().getRoomType().equals(standard)) success = false;
                if(slot.getStatus().equals(Slot.Status.FREE)) success = false;
                if(!success) break;
            }

            Optional<Room> toAdd = roomRepository.findByNumber(entry.getKey());
            if(toAdd.isPresent()) toReturn.add(toAdd.get());
            else throw new NonExistingRoomException(entry.getKey());

        }
        return toReturn;
    }

    @Override
    @Transactional
    public boolean deleteRoom(int number) {
        if(roomRepository.existsByNumber(number)){
            roomRepository.deleteByNumber(number);
            return true;
        }else return false;
    }

    @Override
    @Transactional
    public Optional<Room> updateRoom(int number, Room room) {

        Optional<Room> toUpdate = roomRepository.findByNumber(number);
        if(toUpdate.isPresent() && room != null){

            float price = room.getPrice();
            int capacity = room.getCapacity();
            Room.RoomType type = room.getRoomType();

            try {
                if(price != 0) toUpdate.get().setPrice(price);
                if(capacity != 0) toUpdate.get().setCapacity(capacity);
                if(type != null) toUpdate.get().setRoomType(type);

                roomRepository.save(toUpdate.get());
                return toUpdate;

            } catch (DataAccessException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Room> createRoom(Room parkingPlace) {
        try {
            Room created = roomRepository.save(parkingPlace);
            return Optional.of(created);

        }catch(IllegalArgumentException ex){
            return Optional.empty();
        }
    }
}


