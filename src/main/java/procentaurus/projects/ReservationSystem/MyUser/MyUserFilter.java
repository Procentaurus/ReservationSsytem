package procentaurus.projects.ReservationSystem.MyUser;

import java.time.LocalDate;
import java.util.List;

public class MyUserFilter {

    public static List<MyUser> filterByDateOfBirth(List<MyUser> data, LocalDate date, String mark){
        List<MyUser> results;

        switch (mark) {
            case "==" -> results = data.stream().filter(x -> x.getDateOfBirth().equals(date)).toList();
            case "<=" -> results = data.stream().filter(x -> x.getDateOfBirth().isBefore(date)).toList();
            case ">=" -> results = data.stream().filter(x -> x.getDateOfBirth().isAfter(date)).toList();
            default -> throw new IllegalArgumentException("Invalid comparison mark: " + mark);
        }
        return results;
    }

    public static List<MyUser> filterByLastName(List<MyUser> data, String lastName){
        return data.stream().filter(x -> x.getLastName().equals(lastName)).toList();
    }

    public static List<MyUser> filterByFirstName(List<MyUser> data, String firstName){
        return data.stream().filter(x -> x.getFirstName().equals(firstName)).toList();
    }
}

