package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.entity.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    @Query(value = "SELECT r FROM Reward r WHERE r.sender.id = :id")
    List<Reward> findAllSendersRewards(@Param("id") Long id);

//    @Query(value = "SELECT r FROM Reward r WHERE r.sender.id = :sender_id AND r.receiverEmployee.id = :receiver_id")
//    List<Reward> findAllSendersAndReceiversRewards(@Param("sender_id") Long senderId, @Param("receiver_id") Long receiverId);
}
