package procentaurus.projects.ReservationSystem.StuffMember.Interfaces;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import procentaurus.projects.ReservationSystem.StuffMember.StuffMember;

public interface StuffMemberRepository extends JpaRepository<StuffMember, Long> {
    boolean existsByEmail(@Email @NotBlank String email);
}
