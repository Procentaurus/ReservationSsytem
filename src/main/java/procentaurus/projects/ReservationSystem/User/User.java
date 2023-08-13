package procentaurus.projects.ReservationSystem.User;


import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    protected String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    protected String lastName;

    @Past
    @NotNull
    protected LocalDate dateOfBirth;

    @Size(min = 100000000, max = 999999999)
    @Column(unique = true)
    protected int phoneNumber;

    @Email
    @NotBlank
    @Column(unique = true)
    protected String email;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String password, @NotNull LocalDate dateOfBirth,
                int phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}