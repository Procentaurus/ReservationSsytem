package procentaurus.projects.hotelManager.ParkingPlace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import procentaurus.projects.hotelManager.Exceptions.NonExistingParkingPlaceException;
import procentaurus.projects.hotelManager.Exceptions.NonExistingRoomException;
import procentaurus.projects.hotelManager.Room.Room;
import procentaurus.projects.hotelManager.Slot.Slot;
import procentaurus.projects.hotelManager.Space.Space;

import java.util.*;
import java.util.stream.Collectors;

import static procentaurus.projects.hotelManager.ParkingPlace.ParkingPlaceFilter.filterByVehicleType;
import static procentaurus.projects.hotelManager.ParkingPlace.ParkingPlaceFilter.isFilteringByVehicleTypePossible;
import static procentaurus.projects.hotelManager.Space.SpaceFilter.filterByPrice;
import static procentaurus.projects.hotelManager.Space.SpaceFilter.isFilteringByPricePossible;


@Service
public class ParkingPlaceService implements ParkingPlaceServiceInterface{

    private final ParkingPlaceRepository parkingPlaceRepository;

    @Autowired
    public ParkingPlaceService(ParkingPlaceRepository parkingPlaceRepository) {
        this.parkingPlaceRepository = parkingPlaceRepository;
    }

    @Override
    public Optional<ParkingPlace> findSingleParkingPlace(int number) {
        return parkingPlaceRepository.findByNumber(number);
    }

    @Override
    public List<ParkingPlace> findParkingPlaces(Map<String, String> params) {
        List<ParkingPlace> all = parkingPlaceRepository.findAll();

        if(params != null) {
            if (params.containsKey("price"))
                if(isFilteringByPricePossible(params.get("price")))
                    all = filterByPrice(all.stream().map(x ->(Space) x).toList(),
                            Float.parseFloat(params.get("price").substring(2)),    // value passed
                            params.get("price").substring(0, 2),                              // mark passed, one from [==, <=, >=]
                            ParkingPlace.class);

            if (params.containsKey("vehicleType")){
                if(isFilteringByVehicleTypePossible(params.get("vehicleType")))
                    all = filterByVehicleType(all, ParkingPlace.VehicleType.valueOf(params.get("vehicleType")));
            }
        }
        return all;
    }

    @Override
    public List<ParkingPlace> findAvailableParkingPlaces() throws NonExistingParkingPlaceException {

        ArrayList<ParkingPlace> toReturn = new ArrayList<>();
        List<Slot> data = slotRepository.findByRoomIsNotNull();

        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream().collect(Collectors.groupingBy(slot -> slot.getRoom().getNumber()));

        for (Map.Entry<Integer, List<Slot>> entry : slotsByRoomId.entrySet()) {

            List<Slot> slotsInChosenPeriod = entry.getValue().stream().
                    filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays)).toList();

            boolean success = true;
            for (Slot slot : slotsInChosenPeriod) {
                if(slot.getParkingPlace().getVehicleType() != vehicleType) success = false;
                if(slot.getStatus().equals(Slot.Status.FREE)) success = false;
                if(!success) break;
            }

            Optional<ParkingPlace> toAdd = parkingPlaceRepository.findByNumber(entry.getKey());
            if(toAdd.isPresent()) toReturn.add(toAdd.get());
            else throw new NonExistingParkingPlaceException(entry.getKey());

        }
        return toReturn;
    }

    @Override
    public boolean deleteParkingPlace(int number) {
        if(parkingPlaceRepository.existsByNumber(number)){
            parkingPlaceRepository.deleteByNumber(number);
            return true;
        }else return false;
    }

    @Override
    public Optional<ParkingPlace> updateParkingPlace(int number, Map<String, String> params) {
        Optional<ParkingPlace> toUpdate = parkingPlaceRepository.findByNumber(number);
        if(toUpdate.isPresent()){

            Float price = params.containsKey("price") ? Float.parseFloat(params.get("price")) : null;
            Integer capacity = params.containsKey("capacity") ? Integer.parseInt(params.get("capacity")) : null;
            Integer numberToChange = params.containsKey("number") ? Integer.parseInt(params.get("number")) : null;
            ParkingPlace.VehicleType type;
            if (params.containsKey("vehicleType")) try {
                type = ParkingPlace.VehicleType.valueOf(params.get("vehicleType").toUpperCase());
            } catch (IllegalArgumentException e) {
                type = null;
            }
            else type = null;

            try {

                if(price != null) toUpdate.get().setPrice(price);
                if(capacity != null) toUpdate.get().setCapacity(capacity);
                if(numberToChange != null) toUpdate.get().setNumber(number);
                if(type != null) toUpdate.get().setVehicleType(type);

                parkingPlaceRepository.save(toUpdate.get());
                return toUpdate;

            } catch (DataAccessException ex) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ParkingPlace> createParkingPlace(ParkingPlace parkingPlace) {
        try {
            ParkingPlace created = parkingPlaceRepository.save(parkingPlace);
            return Optional.of(created);

        }catch(IllegalArgumentException ex){
            return Optional.empty();
        }
    }
}
