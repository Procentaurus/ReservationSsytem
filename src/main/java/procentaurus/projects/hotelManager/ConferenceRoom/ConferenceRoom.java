package procentaurus.projects.hotelManager.ConferenceRoom;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import procentaurus.projects.hotelManager.Slot.Slot;
import procentaurus.projects.hotelManager.Space.Space;

import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "conferenceRooms")
public class ConferenceRoom extends Space {

    private boolean hasStage;

    @Size(min = 10, max = 200)
    private String description;

    @OneToMany(mappedBy = "conferenceRoom")
    private List<Slot> slots;

    public ConferenceRoom(int number, int floor, float price,
                          @Min(value = 10, message = "The capacity must be set 10 at least.") int capacity, boolean hasStage) {
        super(number, floor, price, capacity);
        this.hasStage = hasStage;
        this.description = "";
        this.slots = new LinkedList<>();
    }

    public ConferenceRoom(int number, int floor, float price,
                          @Min(value = 10, message = "The capacity must be set 10 at least.") int capacity, boolean hasStage,
                          String description) {
        super(number, floor, price, capacity);
        this.hasStage = hasStage;
        this.description = description;
        this.slots = new LinkedList<>();
    }
}
