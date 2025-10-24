package example.parking.persistence.repository;

import example.parking.persistence.entity.ParkingSpotEntity;
import example.parking.core.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, UUID> {

    // Find free spots that can fit the vehicle type
    @Query("""
        SELECT p FROM ParkingSpotEntity p
        WHERE p.isOccupied = false
        AND (
            (:vehicleType = 'MOTORCYCLE') OR
            (:vehicleType = 'CAR' AND p.spotType IN ('MEDIUM', 'LARGE')) OR
            (:vehicleType = 'BUS' AND p.spotType = 'LARGE')
        )
        ORDER BY p.spotType ASC
        """)
    List<ParkingSpotEntity> findFreeSpotsByVehicleType(VehicleType vehicleType);
}
