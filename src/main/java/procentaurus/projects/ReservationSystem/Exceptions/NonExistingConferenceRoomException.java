package procentaurus.projects.ReservationSystem.Exceptions;

public class NonExistingConferenceRoomException extends Exception{

    public NonExistingConferenceRoomException(int number){
        super("Slot related to non existing conference room of number" + String.valueOf(number));
    }
}
