package cz.morosystem.RewardingSystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "period_state")
public class PeriodState {
    @EmbeddedId
    private PeriodStateId id;

    @Column(name = "budget")
    private int budget;

    @Embeddable
    @Data
    public class PeriodStateId implements Serializable {

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "period_id")
        private Long periodId;
    }
}
