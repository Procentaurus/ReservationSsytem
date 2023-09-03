package procentaurus.projects.ReservationSystem.Miscellaneous;

import procentaurus.projects.ReservationSystem.StuffMember.Interfaces.StuffMemberRepository;

public class ContactDataUniquenessChecker {

    public static void checkEmailUniqueness(String emailToUpdate, String emailReceived, StuffMemberRepository repository) throws IllegalArgumentException{
        if (emailReceived != null)
            if(!emailReceived.equals(emailToUpdate)) {
                if (repository.findByEmail(emailReceived).isEmpty()) {
                    return;
                } else throw new IllegalArgumentException("Provided email is already assigned to another guest.");
            }else throw new IllegalArgumentException("Cannot change the email, the provided one is same as the old one.");
    }

    public static void checkPhoneNumberUniqueness(int numberToUpdate, Integer numberReceived, StuffMemberRepository repository) throws IllegalArgumentException {
        if (numberReceived != null)
            if (!(numberReceived == numberToUpdate)) {
                if (repository.findByPhoneNumber(numberReceived).isEmpty()) {
                    return;
                } else throw new IllegalArgumentException("Provided phone number is already assigned to another guest.");
            } else throw new IllegalArgumentException("Cannot change the phone number, the provided one is same as the old one.");
    }

}