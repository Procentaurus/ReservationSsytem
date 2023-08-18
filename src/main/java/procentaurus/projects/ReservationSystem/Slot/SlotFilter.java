package procentaurus.projects.ReservationSystem.Slot;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SlotFilter {

    public static List<Slot> filterByStatus(List<Slot> data, Slot.Status status){
        return data.stream().filter(slot -> slot.getStatus().equals(status)).toList();
    }

    public static Map<Integer, List<Slot>> filterByDate(List<Slot> data, LocalDate startDate, short numberOfDays){

        // Group slots by room ID
        Map<Integer, List<Slot>> slotsByRoomId = data.stream()
                .collect(Collectors.groupingBy(slot -> slot.getRoom().getNumber()));
        LocalDate date = startDate;

        for(int i = 1; i < numberOfDays; i++) {
            for (Map.Entry<Integer, List<Slot>> slotGroup : slotsByRoomId.entrySet()) {

                LocalDate finalDate = date;
                List<Slot> listOfSlotsFiltered = slotGroup.getValue().stream().filter(slot -> slot.getDate().equals(finalDate)).toList();
                slotGroup.setValue(listOfSlotsFiltered);
            }
            date = date.plus(1, ChronoUnit.DAYS);
        }

        return slotsByRoomId;
    }

    public static List<Slot> filterByRoomNumber(List<Slot> data, int roomNumber){
        return data.stream()
                .filter(slot -> slot.getRoom() != null)
                .filter(slot -> slot.getRoom().getNumber() == roomNumber)
                .toList();
    }

    public static List<Slot> filterByConferenceRoomNumber(List<Slot> data, int conferenceRoomNumber){
        return data.stream()
                .filter(slot -> slot.getConferenceRoom() != null)
                .filter(slot -> slot.getConferenceRoom().getNumber() == conferenceRoomNumber)
                .toList();
    }

    public static List<Slot> filterByParkingPlaceNumber(List<Slot> data, int parkingPlaceNumber){
        return data.stream()
                .filter(slot -> slot.getParkingPlace() != null)
                .filter(slot -> slot.getParkingPlace().getNumber() == parkingPlaceNumber)
                .toList();
    }

    public static List<Slot> filterByReservationId(List<Slot> data, Long reservationId){
        return data.stream().filter(slot -> Objects.equals(slot.getReservation().getId(), reservationId)).toList();
    }

    public static List<Slot> filterByDate(List<Slot> data, LocalDate startDate){
        return data.stream().filter(slot -> slot.getDate().equals(startDate)).toList();
    }

    public static boolean isFilteringByStatusPossible(String data){
        Slot.Status status;
        try{
            status = Slot.Status.valueOf(data.toUpperCase());
        }catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }
}
