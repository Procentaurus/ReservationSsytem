package procentaurus.projects.ReservationSystem.Reservation;

import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.util.List;

public class AvailabilityChecker{

    private final int numberOfParkingPlacesForMotorcycles;
    private final int numberOfParkingPlacesForAutocars;
    private final int numberOfParkingPlacesForCars;

    private final int numberOfGuests;

    private final List<Room> roomsAvailable;
    private final List<ConferenceRoom> conferenceRoomsAvailable;
    private final List<ParkingPlace> parkingPlacesAvailable;


    public AvailabilityChecker(int numberOfParkingPlacesForMotorcycles, int numberOfParkingPlacesForAutocars, int numberOfParkingPlacesForCars,
                       int numberOfGuests, boolean onlyRooms, boolean onlyConferenceRooms, List<Room> roomsAvailable,
                       List<ConferenceRoom> conferenceRoomsAvailable, List<ParkingPlace> parkingPlacesAvailable) {
        this.numberOfParkingPlacesForMotorcycles = numberOfParkingPlacesForMotorcycles;
        this.numberOfParkingPlacesForAutocars = numberOfParkingPlacesForAutocars;
        this.numberOfParkingPlacesForCars = numberOfParkingPlacesForCars;
        this.numberOfGuests = numberOfGuests;
        this.roomsAvailable = roomsAvailable;
        this.conferenceRoomsAvailable = conferenceRoomsAvailable;
        this.parkingPlacesAvailable = parkingPlacesAvailable;
    }

    public boolean areThereEnoughSpace(){

        boolean isThereEnoughSpaceForVehicles = checkIfThereAreEnoughSpaceForVehicles();
        boolean isThereEnoughSpaceInRooms = checkIfThereAreEnoughSpaceInRooms();
        boolean isThereEnoughSpaceInConferenceRooms = checkIfThereIsEnoughSpaceInConferenceRooms();

        return isThereEnoughSpaceInRooms && isThereEnoughSpaceForVehicles && isThereEnoughSpaceInConferenceRooms;
    }

    private boolean checkIfThereAreEnoughSpaceForVehicles() {

        int foundPlacesForMotorcycles = 0;
        int foundPlacesForCars = 0;
        int foundPlacesForAutocars = 0;

        if (numberOfParkingPlacesForMotorcycles > 0 || numberOfParkingPlacesForCars > 0 || numberOfParkingPlacesForAutocars > 0) {

            for (ParkingPlace parkingPlace : parkingPlacesAvailable) {

                if (parkingPlace.getVehicleType() == ParkingPlace.VehicleType.MOTORCYCLE) foundPlacesForMotorcycles += 1;
                else if (parkingPlace.getVehicleType() == ParkingPlace.VehicleType.CAR) foundPlacesForCars += 1;
                else foundPlacesForAutocars += 1;

                if (numberOfParkingPlacesForMotorcycles <= foundPlacesForMotorcycles &&
                        numberOfParkingPlacesForCars <= foundPlacesForCars &&
                        numberOfParkingPlacesForAutocars <= foundPlacesForAutocars) break;
            }
        }

        return numberOfParkingPlacesForMotorcycles <= foundPlacesForMotorcycles &&
                numberOfParkingPlacesForCars <= foundPlacesForCars &&
                numberOfParkingPlacesForAutocars <= foundPlacesForAutocars;
    }

    private boolean checkIfThereAreEnoughSpaceInRooms(){

        int foundPlacesForPeople = 0;

        for(Room room : roomsAvailable){
            foundPlacesForPeople += room.getCapacity();

            if(numberOfGuests <= foundPlacesForPeople) break;
        }
        return numberOfGuests <= foundPlacesForPeople;
    }

    private boolean checkIfThereIsEnoughSpaceInConferenceRooms(){
        boolean isEnoughBigConferenceRoom = false;

        for(ConferenceRoom conferenceRoom : conferenceRoomsAvailable){
            if (conferenceRoom.getCapacity() >= numberOfGuests){
                isEnoughBigConferenceRoom = true;
                break;
            }
        }
        return isEnoughBigConferenceRoom;
    }
}