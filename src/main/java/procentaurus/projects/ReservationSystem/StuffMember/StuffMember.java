package procentaurus.projects.ReservationSystem.StuffMember;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.User.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "stuffMembers")
public class StuffMember extends User {

    @NotNull
    private Role role;

    @NotNull
    @Size(min = 8, max = 50)
    protected String password;

    @PastOrPresent
    @NotNull
    private LocalDate employedFrom;

    public enum Role{
        MANAGER,
        ADMIN,
        FRONT_DESK,
        CONCIERGE
    }
}
