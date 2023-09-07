package procentaurus.projects.ReservationSystem.ParkingPlace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import procentaurus.projects.ReservationSystem.Slot.Slot;
import procentaurus.projects.ReservationSystem.Space.Space;

import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name="parkingPlaces")
public class ParkingPlace extends Space {

    protected int capacity = 1;

    @Min(-1)
    @Max(0)
    protected int floor;

    @NotNull
    private VehicleType vehicleType;

    @JsonIgnore
    @OneToMany(mappedBy = "parkingPlace", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Slot> slots;

    public enum VehicleType {
        CAR,
        AUTOCAR,
        MOTORCYCLE
    }

    public ParkingPlace(int number, int floor, float price, @NotNull VehicleType vehicleType) {
        super(number, floor, price, 1);
        this.vehicleType = vehicleType;
        this.slots = new HashSet<>();
    }
}
