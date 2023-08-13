package procentaurus.projects.ReservationSystem.Reservation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationRepository;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationServiceInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.*;
import static procentaurus.projects.ReservationSystem.Reservation.ReservationFilter.*;

@Service
public class ReservationService implements ReservationServiceInterface {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Optional<Reservation> findSingleReservation(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> findReservations(Map<String, String> params) {

        List<Reservation> all = reservationRepository.findAll();

        if(params != null){

            if(params.containsKey("startDate"))
                if(isFilteringByDatePossible(params.get("startDate")))
                    all = filterByStartDate(all, LocalDate.parse(params.get("startDate")));

            if(params.containsKey("numberOfDays"))
                if(isFilteringByIntPossible(params.get("numberOfDays")))
                    all = filterByNumberOfDays(all, Integer.parseInt(params.get("startDate")));

            if(params.containsKey("guestId"))
                if(isFilteringByLongPossible(params.get("guestId")))
                    all = filterByGuest(all, Long.parseLong(params.get("guestId")));

            if(params.containsKey("guestPhoneNumber"))
                if(isFilteringByIntPossible(params.get("guestPhoneNumber")))
                    all = filterByGuest(all, Integer.parseInt(params.get("guestPhoneNumber")));

            if(params.containsKey("guestEmail"))
                if(isFilteringByEmailPossible(params.get("guestEmail")))
                    all = filterByGuest(all, params.get("guestEmail"));

        }
        return all;
    }

    @Override
    public boolean deleteReservation(Long id) {

        if(reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Reservation> createReservation(List<Integer> roomsNumbers, List<Integer> conferenceRoomsNumbers, List<Integer> parkingPlacesNumbers, List<Guest> guests, LocalDate startDate, short numberOfDays) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> createReservation(List<Integer> roomsNumbers, short sizeOfConferenceRoom, short numberOfParkingPlaces, List<Guest> guests, LocalDate startDate, short numberOfDays) {
        return Optional.empty();
    }
}
