package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.PredefinedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredefinedMessageRepository extends JpaRepository<PredefinedMessage,Long> {
}
