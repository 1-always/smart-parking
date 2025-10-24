package example.parking.persistence.entity;

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
public ParkingTicketEntity() {
    this.id = UUID.randomUUID();
    this.ticketNumber = UUID.randomUUID().toString();
    this.vehicleId = UUID.randomUUID();
    this.spotId = UUID.randomUUID();
    this.entryTime = Instant.now();
    this.exitTime = Instant.now();
    this.feeCents = 0L;

}
    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public void setTicketNumber(String generate) {
        this.ticketNumber = generate;
    }

    public void setVehicleId(UUID uuid) {
        this.vehicleId = uuid;
    }
    public void setSpotId(UUID uuid) {
        this.spotId = uuid;
    }
    public void setEntryTime(Instant instant) {
        this.entryTime = instant;
    }

    public void setExitTime(Instant now) {
        this.exitTime = now;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public void setFeeCents(long l) {
        this.feeCents = l;
    }

    public UUID getSpotId() {
        return spotId;
    }

    public Object getTicketNumber() {
        return ticketNumber;
    }

    public Long getFeeCents() {
    return feeCents;
    }
}