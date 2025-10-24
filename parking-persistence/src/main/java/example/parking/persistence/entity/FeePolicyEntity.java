package example.parking.persistence.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "fee_policy")
public class FeePolicyEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Integer baseMinutes;
    private Long baseAmountCents;
    private Long hourlyAmountCents;
    private Integer roundingMinutes;

    public Long getBaseAmountCents() {
        return baseAmountCents;
    }

    public Integer getBaseMinutes() {
        return baseMinutes;
    }

    public Long getHourlyAmountCents() {
        return hourlyAmountCents;
    }

    public Integer getRoundingMinutes() {
        return roundingMinutes;
    }
}
