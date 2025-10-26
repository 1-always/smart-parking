package example.parking.api.controller;



import example.parking.core.Exception.NoSpotAvailableException;
import example.parking.core.Exception.TicketAlreadyUnparkedException;
import example.parking.core.Exception.TicketNotFoundException;
import example.parking.core.domain.Vehicle;
import example.parking.core.domain.VehicleType;
import example.parking.persistence.entity.ParkingTicketEntity;
import example.parking.persistence.service.ParkingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingServiceImpl service;

    public ParkingController(ParkingServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/entry")
    public ParkingTicketEntity checkIn(@RequestParam String licenseNumber,
                                       @RequestParam VehicleType type) {
        Vehicle vehicle = new Vehicle(licenseNumber, type);
        return service.park(vehicle);
    }

    @PostMapping("/exit")
    public ResponseEntity<?> checkOut(@RequestParam String ticketNumber) {
        try {
            ParkingTicketEntity ticket = service.unpark(ticketNumber);
            return ResponseEntity.ok(ticket);
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ticket not found: " + e.getTicketNumber());
        } catch (TicketAlreadyUnparkedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ticket already unparked: " + e.getTicketNumber());
        } catch (NoSpotAvailableException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Parking spot issue: " + e.getMessage());
        }
    }}
