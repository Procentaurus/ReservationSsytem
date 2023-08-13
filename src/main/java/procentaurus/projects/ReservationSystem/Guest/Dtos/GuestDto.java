package procentaurus.projects.ReservationSystem.Guest.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public  class GuestDto {

    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected int phoneNumber;
    protected String email;
    private boolean signedForNewsletter;
}