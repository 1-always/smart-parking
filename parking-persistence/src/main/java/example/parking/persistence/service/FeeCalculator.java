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
    public static long calculateCents(FeePolicyEntity policy, long durationSeconds) {
        long minutes = Math.max(0L, (durationSeconds + 59) / 60); // ceil to minutes

        long baseCents = policy != null && policy.getBaseAmountCents() != null
                ? policy.getBaseAmountCents()
                : DEFAULT_BASE_CENTS;

        int baseMinutes = policy != null && policy.getBaseMinutes() != null
                ? policy.getBaseMinutes()
                : DEFAULT_BASE_MINUTES;

        long hourlyCents = policy != null && policy.getHourlyAmountCents() != null
                ? policy.getHourlyAmountCents()
                : DEFAULT_HOURLY_CENTS;

        int roundingMinutes = policy != null && policy.getRoundingMinutes() != null
                ? policy.getRoundingMinutes()
                : DEFAULT_ROUNDING_MINUTES;

        if (minutes <= baseMinutes) {
            return baseCents;
        }

        long remaining = minutes - baseMinutes;
        long buckets = (remaining + roundingMinutes - 1) / roundingMinutes; // ceil in roundingUnits

        // convert hourly rate to per-bucket
        long bucketsPerHour = 60L / roundingMinutes;
        long perBucketCents = hourlyCents / bucketsPerHour;

        return baseCents + buckets * perBucketCents;
    }

    /**
     * Convenience wrapper: use Duration.
     */
    public static long calculateCents(FeePolicyEntity policy, Duration duration) {
        Objects.requireNonNull(duration);
        return calculateCents(policy, duration.getSeconds());
    }
}
