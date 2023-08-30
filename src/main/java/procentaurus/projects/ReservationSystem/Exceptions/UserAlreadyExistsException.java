package procentaurus.projects.ReservationSystem.Exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(){
        super("User of provided email already exists.");
    }
}
