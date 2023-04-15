package cz.morosystem.RewardingSystem.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "current_period_state")
public class CurrentPeriodState {
    @EmbeddedId
    private CurrentPeriodStateId id;

    @Column(name = "budget")
    private int budget;

    @Embeddable
    @Data
    private class CurrentPeriodStateId implements Serializable {

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "period_id")
        private Long periodId;
    }
}
