package procentaurus.projects.ReservationSystem.Guest.Dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.User.Dtos.UserCreationDto;

import java.time.LocalDate;

@Getter
public class GuestCreationDto extends UserCreationDto {

    private final boolean signedForNewsletter;

    public GuestCreationDto(@NotNull String firstName, @NotNull String lastName, LocalDate dateOfBirth, int phoneNumber, String email,
        boolean signedForNewsletter){

        super(firstName, lastName, dateOfBirth, phoneNumber, email);
        this.signedForNewsletter = signedForNewsletter;
    }
}
