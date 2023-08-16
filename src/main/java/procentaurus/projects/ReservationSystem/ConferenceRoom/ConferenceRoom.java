package procentaurus.projects.ReservationSystem.ConferenceRoom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "conferenceRooms")
public class ConferenceRoom extends Space {

    @Min(10)
    @Max(1000)
    private int capacity;

    @NotNull
    private Boolean hasStage;

    @Size(max = 200)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "conferenceRoom")
    private Set<Slot> slots;

    public ConferenceRoom(int number, int floor, float price, int capacity, boolean hasStage) {
        super(number, floor, price, capacity);
        this.hasStage = hasStage;
        this.description = "";
        this.slots = new HashSet<>();
    }

    public ConferenceRoom(int number, int floor, float price, int capacity, boolean hasStage, String description) {
        super(number, floor, price, capacity);
        this.hasStage = hasStage;
        this.description = description;
        this.slots = new HashSet<>();
    }
}
