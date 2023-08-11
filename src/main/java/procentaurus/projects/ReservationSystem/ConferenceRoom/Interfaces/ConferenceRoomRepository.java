package procentaurus.projects.ReservationSystem.ConferenceRoom.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.ConferenceRoom.ConferenceRoom;

import java.util.Optional;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    Optional<ConferenceRoom> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
