package procentaurus.projects.ReservationSystem.Reservation;


import org.springframework.stereotype.Service;
import procentaurus.projects.ReservationSystem.Reservation.Interfaces.ReservationServiceInterface;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ReservationService implements ReservationServiceInterface {

    @Override
    public Optional<Reservation> findSingleReservation(int number) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findReservations(Map<String, String> params) {
        return null;
    }

    @Override
    public boolean deleteReservation(int number) {
        return false;
    }

    @Override
    public Optional<Reservation> createReservation(Room parkingPlace) {
        return Optional.empty();
    }
}
