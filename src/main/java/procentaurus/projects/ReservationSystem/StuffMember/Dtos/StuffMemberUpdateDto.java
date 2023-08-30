package procentaurus.projects.ReservationSystem.StuffMember.Dtos;

import procentaurus.projects.ReservationSystem.StuffMember.StuffMember;
import procentaurus.projects.ReservationSystem.User.Dtos.UserUpdateDto;

public class StuffMemberUpdateDto extends UserUpdateDto {

    private StuffMember.Role role;

    @Override
    public boolean isValid() {
        return super.isValid();
    }
}
