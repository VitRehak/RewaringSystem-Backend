package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/role")
public class RoleController {

    final
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    //ALL
    @GetMapping(path = "/all", produces = "application/json")
    public List<Role> roles() {
        return roleService.getAllRoles();
    }
}
