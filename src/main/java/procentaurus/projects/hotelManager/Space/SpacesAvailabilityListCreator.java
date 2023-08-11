package procentaurus.projects.hotelManager.Space;


import procentaurus.projects.hotelManager.ParkingPlace.ParkingPlace;
import procentaurus.projects.hotelManager.Room.Room;
import procentaurus.projects.hotelManager.Slot.Slot;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpacesAvailabilityListCreator {

    public static List<Integer> createSlotAvailabilityListForRooms(
            List<Slot> data, LocalDate startDate, short numberOfDays, Room.RoomType standard, boolean viewForLake, boolean forSmokingPeople){

        ArrayList<Integer> toReturn = new ArrayList<>();
        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream()
                .filter(slot -> slot.getRoom() != null)
                .collect(Collectors.groupingBy(slot -> slot.getRoom().getNumber()));

        for (Map.Entry<Integer, List<Slot>> entry : slotsByRoomId.entrySet()) {

            List<Slot> slotsInChosenPeriod = entry.getValue().stream().
                    filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays)).toList();

            boolean success = true;
            for (Slot slot : slotsInChosenPeriod) {
                if(slot.getRoom().isSmokingAllowed() != forSmokingPeople) success = false;
                if(slot.getRoom().isHasLakeView() == viewForLake) success = false;
                if(slot.getRoom().getRoomType().equals(standard)) success = false;
                if(slot.getStatus().equals(Slot.Status.FREE)) success = false;
                if(!success) break;
            }

            if(success) toReturn.add(entry.getKey());

        }
        return toReturn;
    }

    public static List<Integer> createSlotAvailabilityListForConferenceRooms(
            List<Slot> data, LocalDate startDate, int numberOfDays, boolean hasStage){

        ArrayList<Integer> toReturn = new ArrayList<>();

        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream()
                .filter(slot -> slot.getConferenceRoom() != null)
                .collect(Collectors.groupingBy(slot -> slot.getConferenceRoom().getNumber()));

        for (Map.Entry<Integer, List<Slot>> entry : slotsByRoomId.entrySet()) {

            List<Slot> slotsInChosenPeriod = entry.getValue().stream().
                    filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays)).toList();

            boolean success = true;
            for (Slot slot : slotsInChosenPeriod) {
                if(slot.getConferenceRoom().isHasStage() != hasStage) success = false;
                if(slot.getStatus().equals(Slot.Status.FREE)) success = false;
                if(!success) break;
            }

            if(success) toReturn.add(entry.getKey());

        }
        return toReturn;
    }

    public static List<Integer> createSlotAvailabilityListForParkingPlaces(
            List<Slot> data, LocalDate startDate, int numberOfDays, ParkingPlace.VehicleType vehicleType){

        ArrayList<Integer> toReturn = new ArrayList<>();

        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream()
                .filter(slot -> slot.getParkingPlace() != null)
                .collect(Collectors.groupingBy(slot -> slot.getConferenceRoom().getNumber()));

        for (Map.Entry<Integer, List<Slot>> entry : slotsByRoomId.entrySet()) {

            List<Slot> slotsInChosenPeriod = entry.getValue().stream().
                    filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays)).toList();

            boolean success = true;
            for (Slot slot : slotsInChosenPeriod) {
                if(slot.getParkingPlace().getVehicleType() != vehicleType) success = false;
                if(slot.getStatus().equals(Slot.Status.FREE)) success = false;
                if(!success) break;
            }

            if(success) toReturn.add(entry.getKey());

        }
        return toReturn;
    }

    private static boolean checkIfDateIsInPeriod(LocalDate startDate, LocalDate dateToCheck, int numberOfDays){
        return ((dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate)) &&
                (dateToCheck.isEqual(startDate.plusDays(numberOfDays)) || dateToCheck.isBefore(startDate.plusDays(numberOfDays))));
    }
}

