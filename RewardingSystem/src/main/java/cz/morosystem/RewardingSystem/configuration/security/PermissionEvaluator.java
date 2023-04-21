package cz.morosystem.RewardingSystem.configuration.security;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.entity.Reward;
import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.service.EmployeeService;
import cz.morosystem.RewardingSystem.service.GroupOfEmployeesService;
import cz.morosystem.RewardingSystem.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

@Component(value = "permissionEvaluator")
public class PermissionEvaluator {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    GroupOfEmployeesService groupOfEmployeesService;

    @Autowired
    RewardService rewardService;

    public boolean adminRole(OAuth2AuthenticatedPrincipal principal) {
        Employee employee = employeeService.getEmployeeBySub(principal.getAttribute("sub"));
        boolean admin = employee.getRoles().contains(Role.ROLE_ADMIN);
        return admin;
    }

    public boolean groupOwner(OAuth2AuthenticatedPrincipal principal, Long id) {
        Employee employee = employeeService.getEmployeeBySub(principal.getAttribute("sub"));
        GroupOfEmployees groupOfEmployee = groupOfEmployeesService.getGroup(id);
        if (groupOfEmployee == null)
            return false;
        boolean owner = employee.equals(groupOfEmployee.getCreator());
        return owner;
    }

    public boolean rewardSender(OAuth2AuthenticatedPrincipal principal, Long id) {
        Employee employee = employeeService.getEmployeeBySub(principal.getAttribute("sub"));
        Reward reward = rewardService.getReward(id);
        boolean sender = employee.equals(reward.getSender());
        return sender;
    }
}
