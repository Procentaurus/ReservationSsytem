package procentaurus.projects.ReservationSystem.Miscellaneous;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthorityChecker {

    private final UserDetails userDetails;

    public AuthorityChecker(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public boolean hasManagerAuthority(){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("MANAGER"));
    }

    public boolean hasAdminAuthority(){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
    }

    public boolean hasFrontDeskAuthority(UserDetails userDetails){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("FRONT_DESK"));
    }

    public boolean hasConciergeAuthority(){
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities.stream().anyMatch(auth -> auth.getAuthority().equals("CONCIERGE"));
    }
}
