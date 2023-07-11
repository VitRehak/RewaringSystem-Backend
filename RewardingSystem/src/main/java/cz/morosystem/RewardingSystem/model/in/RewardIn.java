package cz.morosystem.RewardingSystem.model.in;

import lombok.Data;

@Data
public class RewardIn {
    private int amountOfMoney;
    private boolean draft;
    private String text;
    private Long receiver;
    private Boolean isGroup;
}
