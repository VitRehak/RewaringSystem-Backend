package cz.morosystem.RewardingSystem.repository;

import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupOfEmployeesRepository extends JpaRepository<GroupOfEmployees, Long> {

    @Query(value = "SELECT * FROM group_of_employees WHERE creator = :id", nativeQuery = true)
    List<GroupOfEmployees> findAllGroupsOfEmployee(@Param("id") Long id);
}
