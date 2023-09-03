package procentaurus.projects.ReservationSystem.Guest.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.Guest.Guest;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByEmail(String email);
    Optional<Guest> findByPhoneNumber(int number);
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
