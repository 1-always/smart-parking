package example.parking.persistence.service;


import example.parking.core.domain.Vehicle;
import example.parking.core.domain.VehicleType;
import example.parking.core.Exception.NoSpotAvailableException;
import example.parking.persistence.entity.ParkingSpotEntity;
import example.parking.persistence.entity.ParkingTicketEntity;
import example.parking.persistence.repository.ParkingSpotRepository;
import example.parking.persistence.repository.ParkingTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingServiceImpl {

    private final ParkingSpotRepository spotRepo;
    private final ParkingTicketRepository ticketRepo;
    private final TicketNumberGenerator ticketGenerator;
    private final FeeCalculator feeCalculator;

    public ParkingServiceImpl(ParkingSpotRepository spotRepo,
                              ParkingTicketRepository ticketRepo,
                              TicketNumberGenerator ticketGenerator,
                              FeeCalculator feeCalculator) {
        this.spotRepo = spotRepo;
        this.ticketRepo = ticketRepo;
        this.ticketGenerator = ticketGenerator;
        this.feeCalculator = feeCalculator;
    }

    @Transactional
    public ParkingTicketEntity park(Vehicle vehicle) {
        List<ParkingSpotEntity> freeSpots = spotRepo.findFreeSpotsByVehicleType(vehicle.getType());
        if (freeSpots.isEmpty()) throw new NoSpotAvailableException();

        ParkingSpotEntity spot = freeSpots.get(0);
        spot.setOccupied(true);
        spotRepo.save(spot);

        ParkingTicketEntity ticket = new ParkingTicketEntity();
        ticket.setId(UUID.randomUUID());
        ticket.setTicketNumber(ticketGenerator.generate());
        ticket.setVehicleId(UUID.randomUUID()); // for demo
        ticket.setSpotId(spot.getId());
        ticket.setEntryTime(Instant.now());
        ticketRepo.save(ticket);

        return ticket;
    }

    @Transactional
    public ParkingTicketEntity unpark(String ticketNumber) {
        ParkingTicketEntity ticket = ticketRepo.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new example.parking.core.Exception.TicketNotFoundException(ticketNumber));
        ticket.setExitTime(Instant.now());

        // Calculate fee (seconds)
        long durationSeconds = ticket.getExitTime().getEpochSecond() - ticket.getEntryTime().getEpochSecond();
        ticket.setFeeCents(feeCalculator.calculateCents(null, durationSeconds));

        // Mark spot free
        ParkingSpotEntity spot = spotRepo.findById(ticket.getSpotId()).orElseThrow();
        spot.setOccupied(false);
        spotRepo.save(spot);

        ticketRepo.save(ticket);
        return ticket;
    }
}

