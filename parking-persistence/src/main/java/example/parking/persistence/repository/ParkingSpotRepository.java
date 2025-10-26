package example.parking.persistence.repository;

import example.parking.core.domain.SpotType;
import example.parking.persistence.entity.ParkingSpotEntity;
import example.parking.core.domain.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotEntity, UUID> {

    @Query("SELECT p FROM ParkingSpotEntity p WHERE p.isOccupied = false AND p.spotType IN :spotTypes ORDER BY p.spotType ASC")
    List<ParkingSpotEntity> findFreeSpotsBySpotTypes(List<SpotType> spotTypes);
}


