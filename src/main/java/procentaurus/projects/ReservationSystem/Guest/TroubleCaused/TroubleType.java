package procentaurus.projects.ReservationSystem.Guest.TroubleCaused;

import lombok.Getter;

@Getter
public enum TroubleType {
    NO_SHOW("No Show", "Guest didn't arrive for the reservation."),
    CANCELLED_BOOKING("Cancelled Booking", "Guest cancelled their reservation."),
    LATE_CHECK_IN("Late Check-In", "Guest arrived after the designated check-in time."),
    LATE_CHECK_OUT("Late Check-Out", "Guest checked out after the designated check-out time."),
    DAMAGE_TO_ROOM("Damage to Room", "Guest caused damage to the room or hotel property."),
    NOISE_COMPLAINT("Noise Complaint", "Guest received a noise complaint from other guests."),
    UNAUTHORIZED_GUESTS("Unauthorized Guests", "Guest had unauthorized visitors in the room."),
    VIOLATION_OF_POLICY("Violation of Policy", "Guest violated hotel policies or rules."),
    DISRUPTIVE_BEHAVIOR("Disruptive Behavior", "Guest exhibited disruptive or unruly behavior."),
    COMPLAINTS("Other Complaints", "Other complaints or issues raised by the guest.");

    private final String label;
    private final String description;

    TroubleType(String label, String description) {
        this.label = label;
        this.description = description;
    }
}