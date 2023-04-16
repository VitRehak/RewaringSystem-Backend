package cz.morosystem.RewardingSystem.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "predefined_message")
public class PredefinedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "predefined_message_id")
    private Long id;

    @Column(name = "text", length = 1000)
    private String text;

}
