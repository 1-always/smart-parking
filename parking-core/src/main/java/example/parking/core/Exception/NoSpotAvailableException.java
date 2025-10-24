package example.parking.core.Exception;

public class NoSpotAvailableException extends RuntimeException {
    public NoSpotAvailableException() {
        super("No parking spot available for this vehicle type.");
    }
}
