package procentaurus.projects.ReservationSystem.Configuration.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private StuffMemberCreationDto stuffMemberCreationDto;
}
