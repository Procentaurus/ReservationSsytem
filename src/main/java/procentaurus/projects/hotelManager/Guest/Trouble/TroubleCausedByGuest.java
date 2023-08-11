package procentaurus.projects.hotelManager.Guest.Trouble;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TroubleCausedByGuest {

    private TroubleType troubleType;
    private LocalDate date;
}
