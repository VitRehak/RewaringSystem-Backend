package cz.morosystem.RewardingSystem.model.in;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class GroupOfEmployeesIn {
    private String name;
    private String description;
}
