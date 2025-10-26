package example.parking.persistence.service;


import example.parking.persistence.entity.FeePolicyEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

/**
 * Fee calculation helper.
 *
 * - Uses FeePolicyEntity when available.
 * - Fallback defaults used when policy missing.
 * - Works in cents (avoid floating money arithmetic).
 */
@Component
public final class FeeCalculator {

    private static final long DEFAULT_BASE_CENTS = 0L;       // default: free base
    private static final int DEFAULT_BASE_MINUTES = 30;      // default free minutes
    private static final long DEFAULT_HOURLY_CENTS = 10000L; // ₹100.00 -> 10000 cents
    private static final int DEFAULT_ROUNDING_MINUTES = 15;  // rounding granularity

    FeeCalculator() {}

    /**
     * Calculate billed amount in cents.
     *
     * @param policy          fee policy may be null (use defaults)
     * @param durationSeconds time in seconds between entry and exit
     * @return billed amount in cents
     */
    private static final long BASE_AMOUNT_CENTS = 5000;   // ₹50
    private static final int BASE_MINUTES = 30;
    private static final long HOURLY_AMOUNT_CENTS = 2000; // ₹20/hour
    private static final int ROUNDING_MINUTES = 15;

    public static long calculateCents(Duration duration) {
        long minutes = duration.toMinutes();

        if (minutes <= BASE_MINUTES) {
            return BASE_AMOUNT_CENTS;
        }

        // Round up to nearest ROUNDING_MINUTES
        long extraMinutes = minutes - BASE_MINUTES;
        long rounded = ((extraMinutes + ROUNDING_MINUTES - 1) / ROUNDING_MINUTES) * ROUNDING_MINUTES;

        long extraHours = (rounded + 59) / 60; // convert to hours
        return BASE_AMOUNT_CENTS + (extraHours * HOURLY_AMOUNT_CENTS);
    }


}
