package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodStateRepository extends JpaRepository<PeriodState,Long> {
    @Query(value = "SELECT * FROM period_state WHERE employee_id = :idEmployee AND period_id = :idPeriod", nativeQuery = true)
    Optional<PeriodState> findByEmployeeAndPeriod(@Param("idEmployee") Long idEmployee, @Param("idPeriod") Long idPeriod);
}
