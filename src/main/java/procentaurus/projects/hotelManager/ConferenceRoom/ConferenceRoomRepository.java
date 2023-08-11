package procentaurus.projects.hotelManager.ConferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    Optional<ConferenceRoom> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
