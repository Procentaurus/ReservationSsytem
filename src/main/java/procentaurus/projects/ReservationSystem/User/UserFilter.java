package procentaurus.projects.ReservationSystem.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.time.LocalDate;
import java.util.List;

public class UserFilter {

    public static List<User> filterByDateOfBirth(List<User> data, LocalDate date, String mark){
        List<User> results;

        switch (mark) {
            case "==" -> results = data.stream().filter(x -> x.getDateOfBirth().equals(date)).toList();
            case "<=" -> results = data.stream().filter(x -> x.getDateOfBirth().isBefore(date)).toList();
            case ">=" -> results = data.stream().filter(x -> x.getDateOfBirth().isAfter(date)).toList();
            default -> throw new IllegalArgumentException("Invalid comparison mark: " + mark);
        }
        return results;
    }

    public static List<User> filterByLastName(List<User> data, String lastName){
        return data.stream().filter(x -> x.getLastName().equals(lastName)).toList();
    }

    public static List<User> filterByFirstName(List<User> data, String firstName){
        return data.stream().filter(x -> x.getFirstName().equals(firstName)).toList();
    }
}

