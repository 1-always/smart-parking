package example.parking.core.Exception;

public class TicketAlreadyUnparkedException extends RuntimeException {

    private final String ticketNumber;

    public TicketAlreadyUnparkedException(String ticketNumber) {
        super("Ticket already unparked: " + ticketNumber);
        this.ticketNumber = ticketNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}
