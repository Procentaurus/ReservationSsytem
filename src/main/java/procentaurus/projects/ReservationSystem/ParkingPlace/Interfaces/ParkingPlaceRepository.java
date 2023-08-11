package procentaurus.projects.ReservationSystem.ParkingPlace.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;

import java.util.Optional;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {
    Optional<ParkingPlace> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
