package procentaurus.projects.hotelManager.ParkingPlace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingPlaceRepository extends JpaRepository<ParkingPlace, Long> {
    Optional<ParkingPlace> findByNumber(int number);
    boolean existsByNumber(int number);
    void deleteByNumber(int number);
}
