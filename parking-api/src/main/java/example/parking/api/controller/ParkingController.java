package example.parking.api.controller;



import example.parking.core.domain.Vehicle;
import example.parking.core.domain.VehicleType;
import example.parking.persistence.entity.ParkingTicketEntity;
import example.parking.persistence.service.ParkingServiceImpl;
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
    public ParkingTicketEntity checkOut(@RequestParam String ticketNumber) {
        return service.unpark(ticketNumber);
    }
}
