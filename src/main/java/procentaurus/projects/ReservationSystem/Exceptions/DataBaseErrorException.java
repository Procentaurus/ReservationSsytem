package procentaurus.projects.ReservationSystem.Exceptions;

public class DataBaseErrorException extends Exception{

    public DataBaseErrorException(){
        super("Error during performing operation on DB.");
    }
}
