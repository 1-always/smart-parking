package example.parking.persistence.repository;

import example.parking.persistence.entity.ParkingTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicketEntity, UUID> {

    Optional<ParkingTicketEntity> findByTicketNumber(String ticketNumber);
}
