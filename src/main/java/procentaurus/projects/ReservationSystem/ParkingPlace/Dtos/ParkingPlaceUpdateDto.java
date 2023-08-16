package procentaurus.projects.ReservationSystem.ParkingPlace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.ParkingPlace.ParkingPlace;

@Getter
@AllArgsConstructor
public class ParkingPlaceUpdateDto {

    private final Float price;
    private final ParkingPlace.VehicleType vehicleType;

    public boolean isValid(){
        if(price != null)
            return price >= 0;

        return true;
    }
}
