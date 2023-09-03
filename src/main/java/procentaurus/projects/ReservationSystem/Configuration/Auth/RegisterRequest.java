package procentaurus.projects.ReservationSystem.Configuration.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private StuffMemberCreationDto stuffMember;
}
