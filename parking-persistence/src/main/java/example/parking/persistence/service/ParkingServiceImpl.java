package example.parking.persistence.service;

import example.parking.core.Exception.TicketAlreadyUnparkedException;
import example.parking.core.Exception.TicketNotFoundException;
import example.parking.core.domain.SpotType;
import example.parking.core.domain.Vehicle;
import example.parking.core.Exception.NoSpotAvailableException;
import example.parking.persistence.entity.ParkingSpotEntity;
import example.parking.persistence.entity.ParkingTicketEntity;
import example.parking.persistence.repository.ParkingSpotRepository;
import example.parking.persistence.repository.ParkingTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        List<SpotType> spotTypes = switch (vehicle.getType()) {
            case MOTORCYCLE -> List.of(SpotType.SMALL, SpotType.MEDIUM, SpotType.LARGE);
            case CAR -> List.of(SpotType.MEDIUM, SpotType.LARGE);
            case BUS -> List.of(SpotType.LARGE);
        };

        List<ParkingSpotEntity> freeSpots = spotRepo.findFreeSpotsBySpotTypes(spotTypes);

        if (freeSpots.isEmpty()) {
            // Throw exception with vehicle type
            throw new NoSpotAvailableException(vehicle.getType().name());
        }

        ParkingSpotEntity spot = freeSpots.get(0);
        spot.setOccupied(true);
        spotRepo.save(spot);

        ParkingTicketEntity ticket = new ParkingTicketEntity();
        ticket.setEntryTime(Instant.now());
        ticket.setSpotId(spot.getId());
        ticket.setVehicleType(vehicle.getType());
        ticket.setTicketNumber(ticketGenerator.generate());
        ticketRepo.save(ticket);

        return ticket;
    }

    @Transactional
    public ParkingTicketEntity unpark(String ticketNumber) {
        ParkingTicketEntity ticket = ticketRepo.findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new TicketNotFoundException(ticketNumber));

        if (ticket.getExitTime() != null) {
            // vehicle already unparked → throw friendly exception
            throw new TicketAlreadyUnparkedException(ticketNumber);
        }

        // set exit time to now
        Instant exitTime = Instant.now();
        ticket.setExitTime(exitTime);

        // calculate duration and fee
        Duration duration = Duration.between(ticket.getEntryTime(), exitTime);
        System.out.println("duration: " + duration);
        long feeCents = FeeCalculator.calculateCents(duration);
        ticket.setFeeCents(feeCents);

        // free the parking spot
        ParkingSpotEntity spot = spotRepo.findById(ticket.getSpotId())
                .orElseThrow(() -> new NoSpotAvailableException("Unknown spot for ticket: " + ticketNumber));
        spot.setOccupied(false);
        spotRepo.save(spot);

        ticketRepo.save(ticket);

        return ticket;
    }
}

