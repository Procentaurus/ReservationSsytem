package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.Guest.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
