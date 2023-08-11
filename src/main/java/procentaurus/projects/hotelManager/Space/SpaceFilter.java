package procentaurus.projects.hotelManager.Space;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SpaceFilter {

    public static <myClass> List<myClass> filterByCapacity(List<Space> data, int value, String mark, Class<? extends Space> myClass){

        List<Space> results;

        switch (mark) {
            case "==" -> results = data.stream().filter(x -> x.getCapacity() == value).toList();
            case "<=" -> results = data.stream().filter(x -> x.getCapacity() <= value).toList();
            case ">=" -> results = data.stream().filter(x -> x.getCapacity() >= value).toList();
            default -> throw new IllegalArgumentException("Invalid comparison mark: " + mark);
        }

        return results.stream().map(x -> (myClass) x).toList();
    }

    public static <myClass> List<myClass> filterByPrice(List<Space> data, float value, String mark, Class<? extends Space> myClass){

        List<Space> results;

        switch (mark) {
            case "==" -> results = data.stream().filter(x -> x.getPrice() == value).toList();
            case "<=" -> results = data.stream().filter(x -> x.getPrice() <= value).toList();
            case ">=" -> results = data.stream().filter(x -> x.getPrice() >= value).toList();
            default -> throw new IllegalArgumentException("Invalid comparison mark: " + mark);
        }

        return results.stream().map(x -> (myClass) x).toList();
    }

    public static boolean isFilteringByCapacityPossible(String data){

        String mark = data.substring(0, 2);
        String[] marks = {"==", "<=", ">="};

        int value;
        try {
            value = Integer.parseInt(data.substring(2));
        } catch (NumberFormatException e) {
            return false;
        }
        if (!Arrays.asList(marks).contains(data.substring(0, 2))) return false;

        return true;
    }
    public static boolean isFilteringByPricePossible(String data){

        String mark = data.substring(0, 2);
        String[] marks = {"==", "<=", ">="};

        float value;
        try {
            value = Float.parseFloat(data.substring(2));
        } catch (NumberFormatException e) {
            return false;
        }
        if (!Arrays.asList(marks).contains(data.substring(0, 2))) return false;

        return true;
    }
}
