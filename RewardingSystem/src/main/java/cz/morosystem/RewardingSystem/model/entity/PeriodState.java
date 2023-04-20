package cz.morosystem.RewardingSystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "period_state")
public class PeriodState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_state_id")
    private Long id;

    @Column(name = "budget")
    private int budget;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "period_id")
    private Long periodId;

}
