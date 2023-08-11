package procentaurus.projects.hotelManager.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
