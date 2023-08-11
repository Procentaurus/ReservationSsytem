package procentaurus.projects.hotelManager.Slot;

import procentaurus.projects.hotelManager.ConferenceRoom.ConferenceRoom;
import procentaurus.projects.hotelManager.ParkingPlace.ParkingPlace;
import procentaurus.projects.hotelManager.Room.Room;

import procentaurus.projects.hotelManager.Exceptions.*;
import procentaurus.projects.hotelManager.Space.Space;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SpacesChooser {

    private final int numberOfParkingPlacesForMotorcycles;
    private final int numberOfParkingPlacesForAutocars;
    private final int numberOfParkingPlacesForCars;

    private final int numberOfGuests;
    private final boolean  onlyRooms;
    private final boolean onlyConferenceRooms;

    private final List<Room> roomsAvailable;
    private final List<ConferenceRoom> conferenceRoomsAvailable;
    private final List<ParkingPlace> parkingPlacesAvailable;


    public SpacesChooser(int numberOfParkingPlacesForMotorcycles, int numberOfParkingPlacesForAutocars, int numberOfParkingPlacesForCars,
                         int numberOfGuests, boolean onlyRooms, boolean onlyConferenceRooms, List<Room> roomsAvailable,
                         List<ConferenceRoom> conferenceRoomsAvailable, List<ParkingPlace> parkingPlacesAvailable) {
        this.numberOfParkingPlacesForMotorcycles = numberOfParkingPlacesForMotorcycles;
        this.numberOfParkingPlacesForAutocars = numberOfParkingPlacesForAutocars;
        this.numberOfParkingPlacesForCars = numberOfParkingPlacesForCars;
        this.numberOfGuests = numberOfGuests;
        this.onlyRooms = onlyRooms;
        this.onlyConferenceRooms = onlyConferenceRooms;
        this.roomsAvailable = roomsAvailable;
        this.conferenceRoomsAvailable = conferenceRoomsAvailable;
        this.parkingPlacesAvailable = parkingPlacesAvailable;
    }

    public List<Room> chooseRooms() throws LackOfSpaceException {
        AvailabilityChecker availabilityChecker = new AvailabilityChecker();
        if(availabilityChecker.areThereEnoughSpace){
            return null;
        }else throw new LackOfSpaceException("No rooms of required capacity available.");

    }

    public ConferenceRoom chooseConferenceRooms() throws AvailabilityCheckingException, LackOfSpaceException {
        AvailabilityChecker availabilityChecker = new AvailabilityChecker();
        if(availabilityChecker.areThereEnoughSpace){

            List<ConferenceRoom> sortedList = conferenceRoomsAvailable.stream().sorted(Comparator.comparingInt(Space::getCapacity)).toList();

            for(ConferenceRoom conferenceRoom : sortedList){
                if(conferenceRoom.getCapacity() >= numberOfGuests){
                    return conferenceRoom;
                }
            }
            throw new AvailabilityCheckingException("No conferenceRoom, while function stated there is.");

        }else throw new LackOfSpaceException("No conferenceRoom of required size available.");
    }

    public List<ParkingPlace> chooseParkingPlaces() throws LackOfSpaceException {
        AvailabilityChecker availabilityChecker = new AvailabilityChecker();
        if(availabilityChecker.areThereEnoughSpace){

            List<ParkingPlace> toReturn = new LinkedList<>();

            for(ParkingPlace parkingPlace : parkingPlacesAvailable){
                
            }
        }else throw new LackOfSpaceException("No parkingPlaces of required capacity available.");

    }

    private class AvailabilityChecker{

        private final boolean areThereEnoughSpace = areThereEnoughSpace();

        private boolean areThereEnoughSpace(){

            boolean isThereEnoughSpaceForVehicles = checkIfThereAreEnoughSpaceForVehicles();
            boolean isThereEnoughSpaceInRooms = false, isThereEnoughSpaceInConferenceRooms = false;

            if (onlyConferenceRooms){
                isThereEnoughSpaceInRooms = true;
                isThereEnoughSpaceInConferenceRooms = checkIfThereIsEnoughSpaceInConferenceRooms();
            }
            else if(onlyRooms){
                isThereEnoughSpaceInConferenceRooms = true;
                isThereEnoughSpaceInRooms = checkIfThereAreEnoughSpaceInRooms();
            }
            else{
                isThereEnoughSpaceInRooms = checkIfThereAreEnoughSpaceInRooms();
                isThereEnoughSpaceInConferenceRooms = checkIfThereIsEnoughSpaceInConferenceRooms();
            }

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

}

//    private void performReservation(){
//        if(reservation.isOnlyConferenceRoom()) performReservationOnConferenceRooms();
//        else if(reservation.isOnlyRooms()) performReservationOnRooms();
//        else{
//            performReservationOnRooms();
//            performReservationOnConferenceRooms();
//        }
//        performReservationOnVehicles();
//    }
//    private void performReservationOnRooms(){
//
//        LocalDate[] dateHolder = new LocalDate[] { reservation.getStartDate() };
//        Room[] rooms = dataBase.getSlots().stream()
//                .filter(x -> x.getDate().isEqual(dateHolder[0]))
//                .filter(x -> x.getSpace() instanceof Room)
//                .filter(x -> x.getStatus() == Slot.Status.FREE)
//                .map(x -> (Room)x.getSpace())
//                .filter(x -> {
//                    return ((Room)x).getRoomType() == reservation.getChosenRoomStandard() &&
//                            x.isSmokingAllowed() == reservation.isForSmokingPeople() &&
//                            x.hasLakeView() == reservation.isViewForLake();
//                })
//                .toArray(Room[]::new);
//
//        dateHolder[0] = dateHolder[0].plusDays(1);
//
//        for(int i=1; i<reservation.getNumberOfDays();i++) {
//
//            Room[] newRooms= (Room[]) dataBase.getSlots().stream()
//                    .filter(x -> x.getDate().isEqual(dateHolder[0]))
//                    .filter(x -> x.getSpace() instanceof Room)
//                    .filter(x -> x.getStatus() == Slot.Status.FREE)
//                    .map(x -> (Room) x.getSpace())
//                    .filter(x -> {
//                        return ((Room)x).getRoomType() == reservation.getChosenRoomStandard() &&
//                                x.isSmokingAllowed() == reservation.isForSmokingPeople() &&
//                                x.hasLakeView() == reservation.isViewForLake();
//                    })
//                    .toArray(Room[]::new);
//
//            rooms = findCrossing(rooms, newRooms);
//            dateHolder[0] = dateHolder[0].plusDays(1);
//        }
//
//        int numberToAccomodateLeft = reservation.getGuestsData().size();
//        ArrayList<Room> chosenRooms = new ArrayList<>();
//
//        for (Room room : rooms) {
//            if(numberToAccomodateLeft >= 4){
//                chosenRooms.add(room);
//                numberToAccomodateLeft -= room.getCapacity();
//            }
//            if(numberToAccomodateLeft == 0) break;
//        }
//        if(numberToAccomodateLeft != 0) {
//            for (Room room : Arrays.stream(rooms).sorted(Comparator.comparingInt(Room::getCapacity)).toArray(Room[]::new)) {
//                chosenRooms.add(room);
//                numberToAccomodateLeft -= room.getCapacity();
//                if(numberToAccomodateLeft <= 0) break;
//            }
//        }
//
//        dataBase.getSlots().stream()
//                .filter(slot -> chosenRooms.stream().anyMatch(x -> x.equals(slot.getSpace())))
//                .filter(slot -> slot.getDate().isAfter(reservation.getStartDate().minusDays(1))
//                        && slot.getDate().isBefore(reservation.getStartDate().plusDays(reservation.getNumberOfDays())))
//                .forEach(x -> x.bookTheSlot(reservation.getGuestsData(),reservation.getId()));
//
//    }
//    private Room[] findCrossing(Room[] array1, Room[] array2){
//        return Arrays.stream(array1)
//                .filter(x -> Arrays.binarySearch(array2, x) >= 0)
//                .toArray(Room[]::new);
//    }
//    private void performReservationOnConferenceRooms(){
//
//        LocalDate[] dateHolder = new LocalDate[] { reservation.getStartDate() };
//        for(int i=0; i < reservation.getNumberOfDays();i++) {
//            dataBase.getSlots().stream()
//                    .filter(x -> x.getSpace() instanceof ConferenceRoom)
//                    .filter(x -> (x.getDate()).equals(dateHolder[0]))
//                    .filter(x -> x.getStatus() == Slot.Status.FREE)
//                    .filter(x -> ((ConferenceRoom) x.getSpace()).getCapacity() >= reservation.getGuestsData().size())
//                    .limit(1)
//                    .forEach(x -> x.bookTheSlot(reservation.getGuestsData(),reservation.getId()));
//
//            dateHolder[0] = dateHolder[0].plusDays(1);
//        }
//    }
//    private void performReservationOnVehicles() {
//
//        LocalDate[] dateHolder = new LocalDate[] { reservation.getStartDate() };
//        ParkingPlace.VehicleType[] types = ParkingPlace.VehicleType.values();
//        int[] limits = {reservation.getNumberOfParkingPlacesForCars(),
//                reservation.getNumberOfParkingPlacesForAutocars(),
//                reservation.getNumberOfParkingPlacesForMotorcycles()};
//
//        for(int i=0; i<reservation.getNumberOfDays();i++) {
//            Slot[] array = dataBase.getSlots().stream()
//                    .filter(x -> x.getStatus() == Slot.Status.FREE)
//                    .filter(x -> x.getSpace() instanceof ParkingPlace)
//                    .filter(x -> (x.getDate()).equals(dateHolder[0]))
//                    .toArray(Slot[]::new);
//
//            int counter = 0;
//            for (ParkingPlace.VehicleType type : types) {
//                Arrays.stream(array)
//                        .filter(x -> ((ParkingPlace) x.getSpace()).getVehicleType() == type)
//                        .limit(limits[counter])
//                        .forEach(x -> x.bookTheSlot(reservation.getGuestsData(),reservation.getId()));
//
//                counter += 1;
//            }
//            dateHolder[0] = dateHolder[0].plusDays(1);
//        }
//    }
//}
