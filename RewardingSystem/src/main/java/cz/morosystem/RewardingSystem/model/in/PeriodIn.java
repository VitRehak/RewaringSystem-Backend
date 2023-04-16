package cz.morosystem.RewardingSystem.model.in;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PeriodIn {
    private LocalDateTime startOfPeriod;
    private LocalDateTime endOfPeriod;
}
