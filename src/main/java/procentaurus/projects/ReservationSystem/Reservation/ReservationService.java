package procentaurus.projects.ReservationSystem.Reservation;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Guest.Guest;
import procentaurus.projects.ReservationSystem.Guest.Interfaces.GuestRepository;
import procentaurus.projects.ReservationSystem.Reservation.Dtos.ReservationCreateDto;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationRepository;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationServiceInterface;
import procentaurus.projects.ReservationSystem.Slot.Interfaces.SlotRepository;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static procentaurus.projects.ReservationSystem.Miscellaneous.FilterPossibilityChecker.*;
import static procentaurus.projects.ReservationSystem.Reservation.ReservationFilter.*;

@Service
public class ReservationService implements ReservationServiceInterface {

    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;
    private final GuestRepository guestRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, SlotRepository slotRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.slotRepository = slotRepository;
        this.guestRepository = guestRepository;
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
    @Transactional
    public boolean deleteReservation(Long id) {

        if(reservationRepository.existsById(id)) {
            Optional<Reservation> reservation = reservationRepository.findById(id);
            if(reservation.isPresent()) {
                reservation.get().getOccupiedSlots().forEach(slot -> slot.setStatus(Slot.Status.FREE));
                reservation.get().getOccupiedSlots().forEach(slot -> slot.setReservation(null));
                reservationRepository.deleteById(id);
                return true;

            }else return false;
        }
        else return false;
    }

    @Override
    public Optional<Reservation> createReservation(ReservationCreateDto creationDto) {

        // Declarations of local variables
        Set<Integer> roomNumbers = creationDto.getRoomNumbers();
        Set<Integer> parkingPlaceNumbers = creationDto.getParkingPlacesNumbers();
        Set<Integer> conferenceRoomNumbers = creationDto.getConferenceRoomNumbers();

        LocalDate startDate = creationDto.getStartDate();
        short numberOfDays = creationDto.getNumberOfDays();
        Set<Guest> guests = creationDto.getGuests();

        // Collecting or slots( proper Spaces and dates)
        Set<Slot> slotsWithParkingPlaces = null, slotsWithConferenceRooms = null, slotsWithRooms = null;

        if(!parkingPlaceNumbers.isEmpty())
            slotsWithParkingPlaces = slotRepository.findByParkingPlaceIsNotNull().stream()
                    .filter(slot -> parkingPlaceNumbers.contains(slot.getParkingPlace().getNumber()))
                    .filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays))
                    .collect(Collectors.toSet());

        if(!conferenceRoomNumbers.isEmpty())
            slotsWithConferenceRooms = slotRepository.findByConferenceRoomIsNotNull().stream()
                    .filter(slot -> conferenceRoomNumbers.contains(slot.getConferenceRoom().getNumber()))
                    .filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays))
                    .collect(Collectors.toSet());

        if(!roomNumbers.isEmpty())
            slotsWithRooms = slotRepository.findByRoomIsNotNull().stream()
                    .filter(slot -> roomNumbers.contains(slot.getRoom().getNumber()))
                    .filter(slot -> checkIfDateIsInPeriod(startDate, slot.getDate(), numberOfDays))
                    .collect(Collectors.toSet());

        // Checkig if selecteed slots that are required for success are FREE
        boolean roomsAvailable = false, conferenceRoomsAvailable = false, parkingPlacesAvailable = false;
        if(slotsWithRooms != null)
            roomsAvailable = slotsWithRooms.stream().allMatch(slot -> slot.getStatus().equals(Slot.Status.FREE));

        if(slotsWithConferenceRooms != null)
            conferenceRoomsAvailable = slotsWithConferenceRooms.stream().allMatch(slot -> slot.getStatus().equals(Slot.Status.FREE));

        if(slotsWithParkingPlaces != null)
            parkingPlacesAvailable = slotsWithParkingPlaces.stream().allMatch(slot -> slot.getStatus().equals(Slot.Status.FREE));

        if(roomsAvailable && conferenceRoomsAvailable && parkingPlacesAvailable){

            // Performing reservation
            for(Guest guest : guests){
                if(!guestRepository.existsById(guest.getId())) guestRepository.save(guest);
            }
            Set<Slot> allSlots = new HashSet<>();
            allSlots.addAll(slotsWithRooms);
            allSlots.addAll(slotsWithConferenceRooms);
            allSlots.addAll(slotsWithParkingPlaces);

            allSlots.forEach(slot -> slot.setStatus(Slot.Status.BOOKED));

            Reservation reservation = new Reservation(startDate, numberOfDays, guests, allSlots);
            reservationRepository.save(reservation);

            return Optional.of(reservation);

        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<Reservation> createReservation(Set<Integer> roomsNumbers, short sizeOfConferenceRoom, short numberOfParkingPlaces,
                                                   Set<Guest> guests, LocalDate startDate, short numberOfDays) {
        return Optional.empty();
    }

}
