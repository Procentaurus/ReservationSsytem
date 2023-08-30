package procentaurus.projects.ReservationSystem.StuffMember;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import procentaurus.projects.ReservationSystem.User.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stuffMembers")
public class StuffMember extends User {

    @NotNull
    private Role role;

    @NotNull
    @Size(min = 8, max = 50)
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

    public enum Role{
        MANAGER,
        ADMIN,
        FRONT_DESK,
        CONCIERGE
    }
}
