package procentaurus.projects.hotelManager.StuffMember;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import procentaurus.projects.hotelManager.User.User;

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

    @PastOrPresent
    @NotNull
    private LocalDate employedFrom;

    public enum Role{
        MANAGER,
        HOUSEKEEPER,
        FRONT_DESK,
        MAINTENANCE,
        CONCIERGE
    }
}
