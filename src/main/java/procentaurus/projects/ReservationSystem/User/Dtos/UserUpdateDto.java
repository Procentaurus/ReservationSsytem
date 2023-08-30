package procentaurus.projects.ReservationSystem.User.Dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UserUpdateDto {

    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected Integer phoneNumber;
    protected String email;

    public boolean isValid(){
        if(firstName != null && (firstName.length() < 3 || firstName.length() > 20)) return false;
        if(lastName != null && (lastName.length() < 3 || lastName.length() > 30)) return false;
        if(dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now())) return false;
        if(phoneNumber != null && (phoneNumber > 999999999 || phoneNumber < 100000000)) return false;
        if(email != null && (!email.contains("@") || email.length() < 7 || !email.contains("."))) return false;

        return true;
    }
}
