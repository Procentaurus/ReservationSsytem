package procentaurus.projects.ReservationSystem.StuffMember.Dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import procentaurus.projects.ReservationSystem.StuffMember.Role;
import procentaurus.projects.ReservationSystem.MyUser.Dtos.MyUserCreationDto;

import java.time.LocalDate;

@Getter
@Setter
public class StuffMemberCreationDto extends MyUserCreationDto {

    @NotNull
    @Size(min = 8, max = 50)
    private final String password;

    @NotNull
    @Size(min = 8, max = 50)
    private final String passwordConfirmation;

    private final Role role;

    public StuffMemberCreationDto(String firstName, String lastName, LocalDate dateOfBirth, int phoneNumber, String email,
                                  @NotNull String password, @NotNull String passwordConfirmation, @NotNull Role role) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email);
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
    }
}
