package procentaurus.projects.ReservationSystem.Exceptions;

public class NonExistingRoomException extends Exception{

    public NonExistingRoomException(int number){
        super("Slot related to non existing room of number" + String.valueOf(number));
    }
}
