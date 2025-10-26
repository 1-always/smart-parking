package example.parking.persistence.service;

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

        ParkingTicketEntity ticket = null;
        if (freeSpots.isEmpty()) throw new NoSpotAvailableException(ticket.getSpotId());

        ParkingSpotEntity spot = freeSpots.get(0);
        spot.setOccupied(true);
        spotRepo.save(spot);

        ticket = new ParkingTicketEntity();
        ticket.setEntryTime(Instant.now());
        ticket.setSpotId(spot.getId());
        ticket.setVehicleType(vehicle.getType()); // store the type
        ticketRepo.save(ticket);

        return ticket;
    }

    @Transactional
        public ParkingTicketEntity unpark(String ticketNumber){
            ParkingTicketEntity ticket = ticketRepo.findByTicketNumber(ticketNumber)
                    .orElseThrow(() -> new TicketNotFoundException(ticketNumber));

            if (ticket.getExitTime() != null) {
                throw new IllegalStateException("Ticket already unparked");
            }

            Instant exitTime = Instant.now();
            ticket.setExitTime(exitTime);

            Duration duration = Duration.between(ticket.getEntryTime(), exitTime);

            // calculate fee in cents using FeeCalculator (policy=null)
            long feeCents = feeCalculator.calculateCents(null, duration);
            ticket.setFeeCents(feeCents);

            // mark parking spot free
            ParkingSpotEntity spot = spotRepo.findById(ticket.getSpotId())
                    .orElseThrow(() -> new NoSpotAvailableException(ticket.getSpotId()));
            spot.setOccupied(false);
            spotRepo.save(spot);

            ticketRepo.save(ticket);

            return ticket;
        }
}

