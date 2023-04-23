package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period,Long> {

    @Query(value = "SELECT * FROM period WHERE now() BETWEEN start_of_period AND end_of_period;", nativeQuery = true)
    Optional<Period> currentPeriod();
}

