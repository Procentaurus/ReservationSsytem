package procentaurus.projects.hotelManager.ParkingPlace;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import procentaurus.projects.hotelManager.Slot.Slot;
import procentaurus.projects.hotelManager.Space.Space;

import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@Table(name="parkingPlaces")
public class ParkingPlace extends Space {

    @NotNull
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "parkingPlace")
    private List<Slot> slots;

    public enum VehicleType {
        CAR,
        AUTOCAR,
        MOTORCYCLE
    }

    public ParkingPlace(int number, int floor, float price, @Min(1) @Max(1) int capacity, @NotNull VehicleType vehicleType) {
        super(number, floor, price, capacity);
        this.vehicleType = vehicleType;
        this.slots = new LinkedList<>();
    }
}
