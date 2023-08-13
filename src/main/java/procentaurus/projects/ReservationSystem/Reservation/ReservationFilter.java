package procentaurus.projects.ReservationSystem.Reservation;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ReservationFilter {

    public static List<Reservation> filterByNumberOfDays(List<Reservation> data, int numberOfDays){
        return data.stream().filter(x -> x.getNumberOfDays() == numberOfDays).toList();
    }

    public static List<Reservation> filterByStartDate(List<Reservation> data, LocalDate startDate){
        return data.stream().filter(x -> x.getStartDate().equals(startDate)).toList();
    }

    public static List<Reservation> filterByRoomNumber(List<Reservation> data, short roomNumber){
        return data.stream()
                .filter(reservation ->
                        reservation.getOccupiedSlots().stream()
                                .anyMatch(slot -> slot.getRoom() != null && slot.getRoom().getNumber() == roomNumber)
                )
                .toList();
    }

    public static List<Reservation> filterByConferenceRoomNumber(List<Reservation> data, short conferenceRoomNumber){
        return data.stream()
                .filter(reservation ->
                        reservation.getOccupiedSlots().stream()
                                .anyMatch(slot -> slot.getConferenceRoom() != null && slot.getConferenceRoom().getNumber() == conferenceRoomNumber)
                )
                .toList();
    }

    public static List<Reservation> filterByParkingPlaceNumber(List<Reservation> data, short parkingPlaceNumber){
        return data.stream()
                .filter(reservation ->
                        reservation.getOccupiedSlots().stream()
                                .anyMatch(slot -> slot.getParkingPlace() != null && slot.getParkingPlace().getNumber() == parkingPlaceNumber)
                )
                .toList();
    }

    public static List<Reservation> filterByGuest(List<Reservation> data, String email){
        return data.stream().filter(reservation ->
                    reservation.getGuests().stream().anyMatch(guest -> guest.getEmail().equals(email))).toList();
    }

    public static List<Reservation> filterByGuest(List<Reservation> data, int phoneNumber){
        return data.stream().filter(reservation ->
                reservation.getGuests().stream().anyMatch(guest -> guest.getPhoneNumber() == phoneNumber)).toList();
    }

    public static List<Reservation> filterByGuest(List<Reservation> data, Long id){
        return data.stream().filter(reservation ->
                reservation.getGuests().stream().anyMatch(guest -> Objects.equals(guest.getId(), id))).toList();
    }
}