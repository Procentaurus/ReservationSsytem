package procentaurus.projects.ReservationSystem.StuffMember.Interfaces;

import procentaurus.projects.ReservationSystem.Exceptions.UserAlreadyExistsException;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberCreationDto;
import procentaurus.projects.ReservationSystem.StuffMember.Dtos.StuffMemberUpdateDto;
import procentaurus.projects.ReservationSystem.StuffMember.StuffMember;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StuffMemberServiceInterface {

    Optional<StuffMember> findSingleStuffMember(Long id);

    List<StuffMember> findStuffMembers(Map<String, String> params);

    boolean deleteStuffMember(Long id);

    Optional<StuffMember> updateStuffMember(Long id, StuffMemberUpdateDto stuffMember);
}
