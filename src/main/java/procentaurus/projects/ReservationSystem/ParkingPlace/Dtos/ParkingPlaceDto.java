package procentaurus.projects.ReservationSystem.ParkingPlace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;

@Getter
@AllArgsConstructor
public class ParkingPlaceDto {

    private final Integer capacity;
    private final Float price;
    private final ParkingPlace.VehicleType vehicleType;

    public boolean isValid(){
        if(capacity != null)
            if(capacity != 1) return false;
        if(price != null)
            if(price < 0) return false;

        return true;
    }
}
