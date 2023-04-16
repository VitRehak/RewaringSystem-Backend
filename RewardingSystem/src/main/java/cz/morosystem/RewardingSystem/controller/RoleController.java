package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(path = "/role")
public class RoleController {

    @GetMapping(path = "/all", produces = "application/json")
    public List<Role> roles(){
        return List.of(Role.class.getEnumConstants());
    }
}
