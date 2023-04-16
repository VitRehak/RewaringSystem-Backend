package cz.morosystem.RewardingSystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long id;

    @Column(name = "amount_of_money", nullable = false)
    private int amountOfMoney;
    @Column(name = "draft", nullable = false)
    private boolean draft;

    @Column(name = "text", length = 1000)
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender", nullable = false)
    private Employee sender;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver", nullable = false)
    private Employee receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "period_id")
    private Period period;
}
