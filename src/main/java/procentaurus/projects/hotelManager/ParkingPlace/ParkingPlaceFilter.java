package procentaurus.projects.hotelManager.ParkingPlace;

import java.util.List;

public class ParkingPlaceFilter {

    public static List<ParkingPlace> filterByVehicleType(List<ParkingPlace> data, ParkingPlace.VehicleType type){
        return data.stream().filter(x -> x.getVehicleType().equals(type)).toList();
    }

    public static boolean isFilteringByVehicleTypePossible(String data){
        ParkingPlace.VehicleType type;
        try{
            type = ParkingPlace.VehicleType.valueOf(data.toUpperCase());
        }catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }
}
