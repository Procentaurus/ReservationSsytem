package procentaurus.projects.ReservationSystem.Space;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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

    @Min(-1)
    @Max(5)
    protected int floor;

    @Positive
    protected float price;

    private int capacity;
}
