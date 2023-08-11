package procentaurus.projects.ReservationSystem.Room;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name="rooms")
public class Room extends Space {

    @NotNull
    private RoomType roomType;

    @Size(min = 1, max = 4)
    private int capacity;

    private boolean isSmokingAllowed;

    private boolean hasLakeView;

    @NotNull
    @Size(min = 10, max = 200)
    private String description;

    @OneToMany(mappedBy = "room")
    private List<Slot> slots;

    public Room(int number, int floor, float price, @Size(min = 1, max = 4) int capacity, @NotNull RoomType roomType,
            boolean isSmokingAllowed, boolean hasLakeView, @NotNull String description) {
        super(number, floor, price, capacity);
        this.roomType = roomType;
        this.isSmokingAllowed = isSmokingAllowed;
        this.hasLakeView = hasLakeView;
        this.slots = new LinkedList<>();
        this.description = description;
    }

    public Room(int number, int floor, float price, @Size(min = 1, max = 4) int capacity, @NotNull RoomType roomType,
                boolean isSmokingAllowed, boolean hasLakeView) {
        super(number, floor, price, capacity);
        this.roomType = roomType;
        this.isSmokingAllowed = isSmokingAllowed;
        this.hasLakeView = hasLakeView;
        this.slots = new LinkedList<>();
        this.description = "";
    }

    public enum RoomType{
        ECONOMY,
        PREMIUM,
        PRESIDENTIAL
    }
}
