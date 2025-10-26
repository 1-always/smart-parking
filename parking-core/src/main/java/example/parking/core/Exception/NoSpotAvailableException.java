package example.parking.core.Exception;

import java.util.UUID;

public class NoSpotAvailableException extends RuntimeException {
    public NoSpotAvailableException(UUID spotId) {
        super("No parking spot available for this vehicle type.");
    }
}
