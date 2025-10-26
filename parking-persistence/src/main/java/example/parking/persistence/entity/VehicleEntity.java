package example.parking.persistence.entity;

import example.parking.core.domain.VehicleType;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}