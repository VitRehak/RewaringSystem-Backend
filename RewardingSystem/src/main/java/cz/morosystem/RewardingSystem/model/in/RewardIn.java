package cz.morosystem.RewardingSystem.model.in;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Period;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardIn {
    private int amountOfMoney;
    private boolean draft;
    private String text;
    private Long receiver;
}
