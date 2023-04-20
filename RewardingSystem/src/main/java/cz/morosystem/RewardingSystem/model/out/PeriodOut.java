package cz.morosystem.RewardingSystem.model.out;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PeriodOut {
    private Long id;
    private boolean billed;
    private LocalDateTime startOfPeriod;
    private LocalDateTime endOfPeriod;

}
