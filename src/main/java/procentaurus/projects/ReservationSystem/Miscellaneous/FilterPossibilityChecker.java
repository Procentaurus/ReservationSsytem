package procentaurus.projects.ReservationSystem.Miscellaneous;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterPossibilityChecker {

    public static boolean checkIfDateIsInPeriod(LocalDate startDate, LocalDate dateToCheck, int numberOfDays){
        return ((dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate)) &&
                (dateToCheck.isEqual(startDate.plusDays(numberOfDays - 1)) || dateToCheck.isBefore(startDate.plusDays(numberOfDays - 1))));
    }

    public static boolean isFilteringByDatePossible(String date){
        try{
            LocalDate tryDate = LocalDate.parse(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isFilteringByIntPossible(String text){
        try{
            int stasiu = Integer.parseInt(text);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isFilteringByLongPossible(String text){
        try{
            Long stasiu = Long.parseLong(text);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isFilteringByEmailPossible(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
