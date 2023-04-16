package cz.morosystem.RewardingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "period")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "period_id")
    private long id;
    @Column(name = "billed")
    private boolean billed;
    @Column(name = "start_of_period")
    private LocalDateTime startOfPeriod;
    @Column(name = "end_of_period")
    private LocalDateTime endOfPeriod;
}
