package parking_api.parking_api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableAutoConfiguration
// Scan core module for services, utilities, and domain logic
@ComponentScan(basePackages = {
        "example.parking.api.controller",
        "example.parking.core",
        "example.parking.persistence"
})
// Scan entities in persistence module
@EntityScan(basePackages = "example.parking.persistence.entity")
// Enable JPA repositories in persistence module
@EnableJpaRepositories(basePackages = "example.parking.persistence.repository")
public class ParkingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingApiApplication.class, args);
    }
}
