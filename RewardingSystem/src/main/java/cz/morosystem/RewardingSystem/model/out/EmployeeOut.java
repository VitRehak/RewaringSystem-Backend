package cz.morosystem.RewardingSystem.model.out;

import cz.morosystem.RewardingSystem.model.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeOut {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
//    private List<Role> roles = new ArrayList<>();
}
