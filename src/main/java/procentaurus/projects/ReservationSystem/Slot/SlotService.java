package procentaurus.projects.ReservationSystem.Slot;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces.ConferenceRoomRepository;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces.ParkingPlaceRepository;
import procentaurus.projects.ReservationSystem.Room.Interfaces.RoomRepository;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreationDto;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotRepository;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotServiceInterface;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;
import java.util.*;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.*;
import static procentaurus.projects.ReservationSystem.Slot.SlotFilter.*;


@Service
public class SlotService implements SlotServiceInterface {

    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;
    private final ParkingPlaceRepository parkingPlaceRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    public SlotService(SlotRepository slotRepository, RoomRepository roomRepository, ParkingPlaceRepository parkingPlaceRepository, ConferenceRoomRepository conferenceRoomRepository) {
        this.slotRepository = slotRepository;
        this.roomRepository = roomRepository;
        this.parkingPlaceRepository = parkingPlaceRepository;
        this.conferenceRoomRepository = conferenceRoomRepository;
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
    @Transactional
    public boolean deleteSlot(Long id) {
        if(slotRepository.existsById(id)){
            slotRepository.deleteById(id);
            return true;
        }else return false;
    }

    @Override
    @Transactional
    public Optional<Slot> updateSlot(Long id, Map<String, String> params) {

        Optional<Slot> toUpdate = slotRepository.findById(id);
        if(toUpdate.isPresent()){

            Slot.Status status;
            if (params.containsKey("status")) {
                try {
                    status = Slot.Status.valueOf(params.get("status").toUpperCase());
                } catch (IllegalArgumentException e) {
                    status = null;
                }
            } else status = null;

            if(status != null) toUpdate.get().setStatus(status);

            slotRepository.save(toUpdate.get());
            return toUpdate;
        }
        return Optional.empty();
    }

    @Override
    public List<Slot> createSlot(SlotCreationDto slot) throws DataBaseErrorException {

        Optional<?> spaceForSlot = Optional.empty();
        switch(slot.getType()){
            case ROOM -> spaceForSlot = roomRepository.findByNumber(slot.getNumberOfSpace());
            case PARKING_PLACE -> spaceForSlot = conferenceRoomRepository.findByNumber(slot.getNumberOfSpace());
            case CONFERENCE_ROOM -> spaceForSlot = parkingPlaceRepository.findByNumber(slot.getNumberOfSpace());
        }

        if(spaceForSlot.isPresent()){

                Object spacePassed = spaceForSlot.get();
                Slot newSlot = new Slot((Space) spacePassed, slot.getDate());
                ArrayList<Slot> toReturn = new ArrayList<>();
                Slot created = null;

                if (slot.getNumberOfDays() != null && slot.getNumberOfDays() <= 90) {
                    for (int i = 1; i <= slot.getNumberOfDays(); i++) {
                        try {
                            created = slotRepository.save(newSlot);
                        }catch (Exception e){
                            throw new DataBaseErrorException();
                        }
                        toReturn.add(created);

                        newSlot = new Slot((Space) spacePassed, slot.getDate().plusDays(i));
                    }
                } else if (slot.getNumberOfDays() == null) {
                    try {
                        created = slotRepository.save(newSlot);
                    }catch (Exception e){
                        throw new DataBaseErrorException();
                    }
                    toReturn.add(created);
                } else throw new IllegalArgumentException("Number of days must be less or equal to 90.");

                return toReturn;

        }else throw new IllegalArgumentException("No space of given number and type found.");
    }
}
