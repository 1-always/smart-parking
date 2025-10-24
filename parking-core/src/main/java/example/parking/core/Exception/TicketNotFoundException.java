package example.parking.core.Exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(String ticketNumber) {
        super("Ticket not found: " + ticketNumber);
    }
}