package cz.morosystem.RewardingSystem.model.out;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupOfEmployeesOut {

    private Long id;
    private String name;
    private String description;
    private List<Employee> members = new ArrayList<>();
}
