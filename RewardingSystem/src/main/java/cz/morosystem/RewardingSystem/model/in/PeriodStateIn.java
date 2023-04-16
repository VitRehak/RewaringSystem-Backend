package cz.morosystem.RewardingSystem.model.in;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PeriodStateIn {
    private int budget;
}
