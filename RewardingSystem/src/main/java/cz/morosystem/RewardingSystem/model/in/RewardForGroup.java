package cz.morosystem.RewardingSystem.model.in;

import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import lombok.Data;

import java.util.List;

@Data
public class RewardForGroup {
    private int amountOfMoney;
    private boolean draft;
    private String text;
    private Long receivers;
}
