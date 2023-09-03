package procentaurus.projects.ReservationSystem.StuffMember;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import procentaurus.projects.ReservationSystem.MyUser.MyUser;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stuffMembers")
public class StuffMember extends MyUser implements UserDetails {

    @NotNull
    @Enumerated
    private Role role;

    @NotNull
    private String password;

    @PastOrPresent
    @NotNull
    private LocalDate employedFrom;

    public StuffMember(@NotNull String firstName, @NotNull String lastName, @NotNull LocalDate dateOfBirth,
                       int phoneNumber, String email, @NotNull Role role, @NotNull String password, @NotNull LocalDate employedFrom) {
        super(firstName, lastName, password, dateOfBirth, phoneNumber, email);
        this.role = role;
        this.password = password;
        this.employedFrom = employedFrom;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
