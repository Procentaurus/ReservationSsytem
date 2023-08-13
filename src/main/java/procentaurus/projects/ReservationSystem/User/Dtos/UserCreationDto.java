package procentaurus.projects.ReservationSystem.User.Dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserCreationDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Size(min = 3, max = 20)
    protected String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    protected String lastName;

    @Past
    protected LocalDate dateOfBirth;

    @Size(min = 100000000, max = 999999999)
    @Column(unique = true)
    protected int phoneNumber;

    @Email
    @NotBlank
    @Column(unique = true)
    protected String email;

    public UserCreationDto(@NotNull String firstName, @NotNull String lastName, LocalDate dateOfBirth, int phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
