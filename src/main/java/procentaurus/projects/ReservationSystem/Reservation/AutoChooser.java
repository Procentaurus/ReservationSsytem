package procentaurus.projects.ReservationSystem.Reservation;

import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Room.Room;

import procentaurus.projects.ReservationSystem.Exceptions.*;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AutoChooser {

    private final int numberOfParkingPlacesForMotorcycles;
    private final int numberOfParkingPlacesForAutocars;
    private final int numberOfParkingPlacesForCars;

    private final int numberOfGuests;

    private final List<ConferenceRoom> conferenceRoomsAvailable;
    private final List<ParkingPlace> parkingPlacesAvailable;


    public AutoChooser(int numberOfParkingPlacesForMotorcycles, int numberOfParkingPlacesForAutocars, int numberOfParkingPlacesForCars,
                       int numberOfGuests, boolean onlyRooms, boolean onlyConferenceRooms,
                       List<ConferenceRoom> conferenceRoomsAvailable, List<ParkingPlace> parkingPlacesAvailable) {
        this.numberOfParkingPlacesForMotorcycles = numberOfParkingPlacesForMotorcycles;
        this.numberOfParkingPlacesForAutocars = numberOfParkingPlacesForAutocars;
        this.numberOfParkingPlacesForCars = numberOfParkingPlacesForCars;
        this.numberOfGuests = numberOfGuests;
        this.conferenceRoomsAvailable = conferenceRoomsAvailable;
        this.parkingPlacesAvailable = parkingPlacesAvailable;
    }

    public ConferenceRoom chooseConferenceRooms() throws AvailabilityCheckingException, LackOfSpaceException {
        List<ConferenceRoom> sortedList = conferenceRoomsAvailable.stream().sorted(Comparator.comparingInt(Space::getCapacity)).toList();

        for(ConferenceRoom conferenceRoom : sortedList){
            if(conferenceRoom.getCapacity() >= numberOfGuests){
                return conferenceRoom;
            }
        }
        throw new AvailabilityCheckingException("No conferenceRoom, while function stated there is.");
    }

    public List<ParkingPlace> chooseParkingPlaces() throws LackOfSpaceException, AvailabilityCheckingException {

        List<ParkingPlace> toReturn = new LinkedList<>();
        short assignedPlacesForCars = 0, assignedPlacesForMotorcycles = 0, assignedPlacesForAutocars = 0;

        for(ParkingPlace parkingPlace : parkingPlacesAvailable){

            if (parkingPlace.getVehicleType() == ParkingPlace.VehicleType.CAR && assignedPlacesForCars < numberOfParkingPlacesForCars) {
                assignedPlacesForCars += 1;
                toReturn.add(parkingPlace);
            }
            if (parkingPlace.getVehicleType() == ParkingPlace.VehicleType.AUTOCAR && assignedPlacesForAutocars < numberOfParkingPlacesForAutocars) {
                assignedPlacesForAutocars += 1;
                toReturn.add(parkingPlace);
            }
            if (parkingPlace.getVehicleType() == ParkingPlace.VehicleType.MOTORCYCLE && assignedPlacesForMotorcycles < numberOfParkingPlacesForMotorcycles) {
                assignedPlacesForMotorcycles += 1;
                toReturn.add(parkingPlace);
            }

            if(assignedPlacesForMotorcycles == numberOfParkingPlacesForMotorcycles &&
                    assignedPlacesForCars == numberOfParkingPlacesForCars &&
                    assignedPlacesForAutocars == numberOfParkingPlacesForAutocars) break;
        }

        if(assignedPlacesForMotorcycles != numberOfParkingPlacesForMotorcycles ||
                assignedPlacesForCars != numberOfParkingPlacesForCars ||
                assignedPlacesForAutocars != numberOfParkingPlacesForAutocars)
            throw new AvailabilityCheckingException("Not enough parkingPlaces, while function stated there is");

        return toReturn;

    }
}