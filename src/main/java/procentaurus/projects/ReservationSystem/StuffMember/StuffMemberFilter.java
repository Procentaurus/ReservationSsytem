package procentaurus.projects.ReservationSystem.StuffMember;

import procentaurus.projects.ReservationSystem.User.User;

import java.time.LocalDate;
import java.util.List;

public class StuffMemberFilter {

    public static List<StuffMember> filterByRole(List<StuffMember> data, StuffMember.Role role){
        return data.stream().filter(x -> x.getRole().equals(role)).toList();
    }

    public static boolean isFilteringByRolePossible(String text){
        try{
            StuffMember.Role role = StuffMember.Role.valueOf(text);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static List<StuffMember> filterByDateEmployedFrom(List<StuffMember> data, LocalDate date, String mark){
        List<StuffMember> results;

        switch (mark) {
            case "==" -> results = data.stream().filter(x -> x.getEmployedFrom().equals(date)).toList();
            case "<=" -> results = data.stream().filter(x -> x.getEmployedFrom().isBefore(date)).toList();
            case ">=" -> results = data.stream().filter(x -> x.getEmployedFrom().isAfter(date)).toList();
            default -> throw new IllegalArgumentException("Invalid comparison mark: " + mark);
        }
        return results;
    }
}
