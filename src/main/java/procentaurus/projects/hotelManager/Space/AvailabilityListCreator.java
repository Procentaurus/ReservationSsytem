package procentaurus.projects.hotelManager.Space;


import java.time.LocalDate;

public class AvailabilityListCreator {


    public static boolean checkIfDateIsInPeriod(LocalDate startDate, LocalDate dateToCheck, int numberOfDays){
        return ((dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate)) &&
                (dateToCheck.isEqual(startDate.plusDays(numberOfDays)) || dateToCheck.isBefore(startDate.plusDays(numberOfDays))));
    }
}

