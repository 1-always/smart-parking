package example.parking.persistence.entity;

import example.parking.core.domain.VehicleType;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "parking_ticket")
public class ParkingTicketEntity {
    @Id
    private UUID id;
    private String ticketNumber;
    private UUID vehicleId;
    private UUID spotId;
    private Instant entryTime;
    private Instant exitTime;
    private Long feeCents;
    private VehicleType vehicleType;
public ParkingTicketEntity() {
    this.id = UUID.randomUUID();
    this.ticketNumber = UUID.randomUUID().toString();
    this.vehicleId = UUID.randomUUID();
    this.spotId = UUID.randomUUID();
    this.entryTime = Instant.now();
    this.exitTime = null;
    this.feeCents = 0L;

}
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public Instant getEntryTime() { return entryTime; }
    public void setEntryTime(Instant entryTime) { this.entryTime = entryTime; }

    public Instant getExitTime() { return exitTime; }
    public void setExitTime(Instant exitTime) { this.exitTime = exitTime; }

    public UUID getSpotId() { return spotId; }
    public void setSpotId(UUID spotId) { this.spotId = spotId; }

    public long getFeeCents() { return feeCents; }
    public void setFeeCents(long feeCents) { this.feeCents = feeCents; }

    public VehicleType getVehicleType() { return vehicleType; }
    public void setVehicleType(VehicleType vehicleType) { this.vehicleType = vehicleType; }
}