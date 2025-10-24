package example.parking.persistence.service;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates human-friendly ticket numbers.
 * Thread-safe and simple.
 *
 * Pattern: TKT-YYYYMMDD-<counter>
 * Counter resets on application restart; for production use stable store or DB sequence.
 */
@Component
public class TicketNumberGenerator {

    private final AtomicLong counter = new AtomicLong(1);

    public String generate() {
        String date = LocalDate.now().toString().replace("-", "");
        long n = counter.getAndIncrement();
        return String.format("TKT-%s-%05d", date, n);
    }
}
