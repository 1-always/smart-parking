package example.parking.core.Exception;

public class NoSpotAvailableException extends RuntimeException {
    private final String vehicleType;

    public NoSpotAvailableException(String vehicleType) {
        super("No parking spot available for vehicle type: " + vehicleType);
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }}
