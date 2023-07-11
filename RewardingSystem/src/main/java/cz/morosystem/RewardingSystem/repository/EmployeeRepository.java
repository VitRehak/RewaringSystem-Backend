package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value =  "SELECT * FROM employee WHERE sub = :sub",nativeQuery = true)
    Optional<Employee> findEmployeeBySub(@Param("sub") String sub);
}
