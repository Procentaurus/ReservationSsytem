package procentaurus.projects.ReservationSystem.StuffMember.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import procentaurus.projects.ReservationSystem.StuffMember.Role;
import procentaurus.projects.ReservationSystem.MyUser.Dtos.MyUserUpdateDto;

@Getter
@AllArgsConstructor
public class StuffMemberUpdateDto extends MyUserUpdateDto {

    private Role role;

    @Override
    public boolean isValid() {
        return super.isValid();
    }
}
