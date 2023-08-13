package procentaurus.projects.ReservationSystem.Guest.TroubleCaused;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleCausedByGuest {

    private TroubleType troubleType;
    private LocalDate date;
}
