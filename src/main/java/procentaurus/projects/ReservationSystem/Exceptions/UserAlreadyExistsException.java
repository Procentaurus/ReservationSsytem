package procentaurus.projects.ReservationSystem.Exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String dataType){
        super("User of provided "+ dataType +" already exists.");
    }
}
