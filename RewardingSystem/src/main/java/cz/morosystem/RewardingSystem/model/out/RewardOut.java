package cz.morosystem.RewardingSystem.model.out;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Period;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RewardOut {

    private Long id;
    private int amountOfMoney;
    private boolean draft;
    private String text;
    private Employee sender;
    private Employee receiver;
    private Boolean isGroup;
    private Period period;
}
