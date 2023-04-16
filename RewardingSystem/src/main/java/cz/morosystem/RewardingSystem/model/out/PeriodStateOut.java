package cz.morosystem.RewardingSystem.model.out;

import lombok.Data;

@Data
public class PeriodStateOut {
    private Long id;
    private int budget;
    private Long employeeId;
    private Long periodId;
}
