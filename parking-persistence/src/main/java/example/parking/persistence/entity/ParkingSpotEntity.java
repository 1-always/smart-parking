package example.parking.persistence.entity;

import example.parking.core.domain.SpotType;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String floorId;
    private String spotNumber;

    @Enumerated(EnumType.STRING)
    private SpotType spotType;

    private boolean isOccupied;

    // getters/setters omitted for brevity


    // getters / setters

    public SpotType getSpotType() {
        return spotType;
    }
    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;

    }
    public boolean isOccupied() {
        return isOccupied;
    }
    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
    public UUID getId() {
        return id;
    }
    public String getFloorId() {
        return floorId;
    }
    public String  getSpotNumber() {
        return spotNumber;
    }
    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public void setId(UUID spotId) {
        this.id = spotId;
    }
}