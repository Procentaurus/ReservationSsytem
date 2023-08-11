package procentaurus.projects.hotelManager.ConferenceRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import procentaurus.projects.hotelManager.Space.Space;

import java.util.*;

import static procentaurus.projects.hotelManager.ConferenceRoom.ConferenceRoomFilter.filterByHasStage;
import static procentaurus.projects.hotelManager.Space.SpaceFilter.*;


@Service
public class ConferenceRoomService implements ConferenceRoomServiceInterface{

    private final ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
    }

    @Override
    public Optional<ConferenceRoom> findSingleConferenceRoom(int number) {
        return conferenceRoomRepository.findByNumber(number);
    }

    @Override
    public List<ConferenceRoom> findConferenceRooms(Map<String, String> params) {
        List<ConferenceRoom> all = conferenceRoomRepository.findAll();

        if(params != null) {
            if (params.containsKey("capacity"))
                if(isFilteringByCapacityPossible(params.get("price")))
                    all = filterByCapacity(all.stream().map(x ->(Space) x).toList(),
                            Integer.parseInt(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            ConferenceRoom.class);

            if (params.containsKey("price"))
                if(isFilteringByPricePossible(params.get("price")))
                    all = filterByPrice(all.stream().map(x ->(Space) x).toList(),
                            Float.parseFloat(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            ConferenceRoom.class);

            if (params.containsKey("hasStage")) all = filterByHasStage(all, params.get("hasStage"));
        }
        return all;
    }

    @Override
    public boolean deleteConferenceRoom(int number) {
        if(conferenceRoomRepository.existsByNumber(number)){
            conferenceRoomRepository.deleteByNumber(number);
            return true;
        }else return false;
    }

    @Override
    public Optional<ConferenceRoom> updateConferenceRoom(int number, Map<String, String> params) {
        Optional<ConferenceRoom> toUpdate = conferenceRoomRepository.findByNumber(number);
        if(toUpdate.isPresent()){

            Float price = params.containsKey("price") ? Float.parseFloat(params.get("price")) : null;
            Integer capacity = params.containsKey("capacity") ? Integer.parseInt(params.get("capacity")) : null;
            Integer numberToChange = params.containsKey("number") ? Integer.parseInt(params.get("number")) : null;
            Boolean hasStage = params.containsKey("hasStage") ? Boolean.parseBoolean(params.get("hasStage")) : null;
            String description = params.getOrDefault("description", null);

            try {

                if(price != null) toUpdate.get().setPrice(price);
                if(capacity != null) toUpdate.get().setCapacity(capacity);
                if(numberToChange != null) toUpdate.get().setNumber(number);
                if(hasStage != null) toUpdate.get().setHasStage(hasStage);
                if(description != null) toUpdate.get().setDescription(description);

                conferenceRoomRepository.save(toUpdate.get());
                return toUpdate;

            } catch (DataAccessException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ConferenceRoom> createConferenceRoom(ConferenceRoom conferenceRoom) {
        try {
            ConferenceRoom created = conferenceRoomRepository.save(conferenceRoom);
            return Optional.of(created);

        }catch(IllegalArgumentException ex){
            return Optional.empty();
        }
    }
}
