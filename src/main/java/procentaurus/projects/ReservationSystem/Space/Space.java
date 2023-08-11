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

    protected Long id;

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

    public Space(int number, int floor, float price, int capacity) {
        this.number = number;
        this.floor = floor;
        this.price = price;
        this.capacity = capacity;
    }
}
