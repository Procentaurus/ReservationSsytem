package procentaurus.projects.ReservationSystem.Slot.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import procentaurus.projects.ReservationSystem.Slot.Slot;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByRoomIsNotNull();
    List<Slot> findByConferenceRoomIsNotNull();
    List<Slot> findByParkingPlaceIsNotNull();
}
