package procentaurus.projects.ReservationSystem.Room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name="rooms")
public class Room extends Space {

    @Min(1)
    @Max(4)
    private int capacity;

    @NotNull
    private RoomType roomType;

    @NotNull
    private Boolean isSmokingAllowed;

    @NotNull
    private Boolean hasLakeView;

    @NotNull
    @Size(max = 200)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<Slot> slots;

    public Room(int number, int floor, float price, int capacity, @NotNull RoomType roomType,
                boolean isSmokingAllowed, boolean hasLakeView, @NotNull String description) {
        super(number, floor, price, capacity);
        this.roomType = roomType;
        this.isSmokingAllowed = isSmokingAllowed;
        this.hasLakeView = hasLakeView;
        this.slots = new HashSet<>();
        this.description = description;
    }

    public Room(int number, int floor, float price, int capacity, @NotNull RoomType roomType,
                boolean isSmokingAllowed, boolean hasLakeView) {
        super(number, floor, price, capacity);
        this.roomType = roomType;
        this.isSmokingAllowed = isSmokingAllowed;
        this.hasLakeView = hasLakeView;
        this.slots = new HashSet<>();
        this.description = "";
    }

    public enum RoomType{
        ECONOMY,
        PREMIUM,
        PRESIDENTIAL
    }
}
