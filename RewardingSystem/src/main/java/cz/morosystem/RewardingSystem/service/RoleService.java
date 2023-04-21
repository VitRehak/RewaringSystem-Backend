package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    public List<Role> getAllRoles() {
        return List.of(Role.class.getEnumConstants());
    }
}
