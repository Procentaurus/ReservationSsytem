package procentaurus.projects.ReservationSystem.StuffMember.Dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.StuffMember.StuffMember;
import procentaurus.projects.ReservationSystem.User.Dtos.UserCreationDto;

import java.time.LocalDate;

@Getter
public class StuffMemberCreationDto extends UserCreationDto {

    @NotNull
    @Size(min = 8, max = 50)
    private final String password;

    @NotNull
    @Size(min = 8, max = 50)
    private final String passwordConfirmation;

    private final StuffMember.Role role;

    public StuffMemberCreationDto(@NotNull String firstName, @NotNull String lastName, LocalDate dateOfBirth, int phoneNumber, String email,
                                  @NotNull String password, @NotNull String passwordConfirmation, @NotNull StuffMember.Role role) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email);
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
    }
}
