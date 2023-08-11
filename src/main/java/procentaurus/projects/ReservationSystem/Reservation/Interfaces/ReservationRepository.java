package procentaurus.projects.ReservationSystem.Reservation.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.Reservation.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
