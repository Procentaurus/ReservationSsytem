package procentaurus.projects.ReservationSystem.Room.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.Room.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
