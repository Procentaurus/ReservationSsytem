package procentaurus.projects.ReservationSystem.Slot;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces.ConferenceRoomRepository;
import procentaurus.projects.ReservationSystem.Exceptions.DataBaseErrorException;
import procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces.ParkingPlaceRepository;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Room.Interfaces.RoomRepository;
import procentaurus.projects.ReservationSystem.Room.Room;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotCreationDto;
import procentaurus.projects.ReservationSystem.Slot.Dtos.SlotUpdateDto;
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

        List<Slot> all = slotRepository.findAll();

        if(params != null) {

            if (params.containsKey("status"))
                if(isFilteringByStatusPossible(params.get("status")))
                    all = filterByStatus(all, Slot.Status.valueOf(params.get("status")));

            if (params.containsKey("date"))
                if(isFilteringByDatePossible(params.get("date")))
                    all = filterByDate(all, LocalDate.parse(params.get("date")));

            if(params.containsKey("reservationId"))
                if(isFilteringByLongPossible(params.get("reservationId")))
                    all = filterByReservationId(all, Long.parseLong(params.get("hasLakeView")));

            if(params.containsKey("roomNumber")) {
                if (isFilteringByIntPossible(params.get("roomNumber")))
                    all = filterByRoomNumber(all, Integer.parseInt(params.get("roomNumber")));
            }
            else if(params.containsKey("conferenceRoomNumber")) {
                if (isFilteringByIntPossible(params.get("conferenceRoomNumber")))
                    all = filterByConferenceRoomNumber(all, Integer.parseInt(params.get("conferenceRoomNumber")));
            }
            else if(params.containsKey("parkingPlaceNumber")) {
                if (isFilteringByIntPossible(params.get("parkingPlaceNumber")))
                    all = filterByParkingPlaceNumber(all, Integer.parseInt(params.get("parkingPlaceNumber")));
            }
        }
        return all;
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
    public Optional<Slot> updateSlot(Long id, SlotUpdateDto slotUpdateDto) {

        Optional<Slot> toUpdate = slotRepository.findById(id);
        if(toUpdate.isPresent()){

            toUpdate.get().setStatus(slotUpdateDto.getStatus());

            slotRepository.save(toUpdate.get());
            return toUpdate;
        }
        return Optional.empty();
    }

    @Override
    public List<Slot> createSlot(SlotCreationDto slot) throws DataBaseErrorException {

        Optional<? extends Space> spaceForSlot = Optional.empty();
        switch(slot.getType()){
            case ROOM -> spaceForSlot = roomRepository.findByNumber(slot.getNumberOfSpace());
            case CONFERENCE_ROOM -> spaceForSlot = conferenceRoomRepository.findByNumber(slot.getNumberOfSpace());
            case PARKING_PLACE -> spaceForSlot = parkingPlaceRepository.findByNumber(slot.getNumberOfSpace());
        }

        if(spaceForSlot.isPresent()){

            Space spacePassed = spaceForSlot.get();
            ArrayList<Slot> toReturn = new ArrayList<>();

            if (slot.getNumberOfDays() != null && slot.getNumberOfDays() <= 90) {
                for (int i = 0; i < slot.getNumberOfDays(); i++) {
                    Slot created = createSlotIfPossible(spacePassed, slot.getDate().plusDays(i));
                    toReturn.add(created);
                }
            } else if (slot.getNumberOfDays() == null) {
                Slot created = createSlotIfPossible(spacePassed, slot.getDate());
                toReturn.add(created);
            }else throw new IllegalArgumentException("Number of days must be less or equal to 90.");

            return toReturn;

        }else throw new IllegalArgumentException("No space of given number and type found.");
    }


    private boolean checkIfSlotAlreadyExists(Space space, LocalDate date){

        List<Slot> all;
        boolean found = true;

        if (space instanceof Room) {
            all = slotRepository.findByRoomIsNotNull();
            found = all.stream().anyMatch(slot -> slot.getDate().equals(date) && slot.getRoom().getNumber() == space.getNumber());
        } else if (space instanceof ConferenceRoom) {
            all = slotRepository.findByConferenceRoomIsNotNull();
            found = all.stream().anyMatch(slot -> slot.getDate().equals(date) && slot.getConferenceRoom().getNumber() == space.getNumber());
        } else{
            all = slotRepository.findByParkingPlaceIsNotNull();
            found = all.stream().anyMatch(slot -> slot.getDate().equals(date) && slot.getParkingPlace().getNumber() == space.getNumber());
        }
        return found;
    }

    private Slot createSlotIfPossible(Space space, LocalDate date) throws DataBaseErrorException {

        Slot newSlot, toReturn;

        if(!checkIfSlotAlreadyExists(space, date))
            newSlot = new Slot(space, date);
        else throw new IllegalArgumentException("Cant create slot with provided data. The slot already exists.");

        try {
            toReturn = slotRepository.save(newSlot);
        }catch (Exception e){
            throw new DataBaseErrorException();
        }

        return toReturn;
    }
}
