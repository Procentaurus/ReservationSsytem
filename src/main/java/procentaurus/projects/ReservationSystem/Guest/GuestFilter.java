package procentaurus.projects.ReservationSystem.Guest;

import java.util.List;

public class GuestFilter {

    public static List<Guest> filterByIsSignedForNewsletter(List<Guest> data, Boolean isSignedForNewsletter){
        return data.stream().filter(x -> x.getSignedForNewsletter().equals(isSignedForNewsletter)).toList();
    }
}
