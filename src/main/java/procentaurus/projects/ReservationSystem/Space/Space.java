package procentaurus.projects.ReservationSystem.Space;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;


@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Space {

    @Id
    @Positive
    @Column(unique = true)
    protected int number;

    @Positive
    protected int floor;

    @Positive
    protected float price;

    @Positive
    private int capacity;
}
