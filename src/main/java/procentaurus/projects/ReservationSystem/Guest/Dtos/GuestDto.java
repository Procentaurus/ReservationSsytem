package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.AllArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
public abstract class GuestDto {

    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected int phoneNumber;
    protected String email;
}