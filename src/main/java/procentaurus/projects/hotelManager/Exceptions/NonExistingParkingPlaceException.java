package procentaurus.projects.hotelManager.Exceptions;

public class NonExistingParkingPlaceException extends Exception{

    public NonExistingParkingPlaceException(int number){
        super("Slot related to non existing parking place of number" + String.valueOf(number));
    }
}
