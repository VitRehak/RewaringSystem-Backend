package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodStateRepository extends JpaRepository<PeriodState,Long> {
    @Query(value = "SELECT * FROM period_state WHERE employee_id = ?1 AND period_id = ?2;", nativeQuery = true)
    PeriodState findByEmployeeAndPeriod(Long idEmployee, Long idPeriod);
}
