package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.GroupOfEmployeesIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.model.out.GroupOfEmployeesOut;
import cz.morosystem.RewardingSystem.service.GroupOfEmployeesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/GroupOfEmployees")
public class GroupOfEmployeesController {

    @Autowired
    GroupOfEmployeesService groupOfEmployeesService;

    ////////////////////////MISSING USER ID---PATH ID IS SUBSTITUTION///////////////////////////////////
    @GetMapping(path = "/myGroups/{id}", produces = "application/json")
    public ResponseEntity<List<GroupOfEmployeesOut>> myGroups(@PathVariable Long id) {
        return ResponseEntity.ok(groupOfEmployeesService.getMyGroups(id));
    }


    ////////////////////////MISSING USER ID---PATH ID IS SUBSTITUTION///////////////////////////////////
    @PostMapping(path = "/Create/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<GroupOfEmployeesOut> createGroup(@RequestBody GroupOfEmployeesIn groupOfEmployeesIn, @PathVariable Long id) {
        return ResponseEntity.ok(groupOfEmployeesService.create(groupOfEmployeesIn, id));
    }

    @GetMapping(path = "/groupMembers/{id}", produces = "application/json")
    public ResponseEntity<List<EmployeeOut>> groupMembers(@PathVariable Long id) {
        List<EmployeeOut> employeeOuts = groupOfEmployeesService.getGroupMembers(id);
        return employeeOuts == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employeeOuts);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        groupOfEmployeesService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<GroupOfEmployeesOut> updateGroup(@RequestBody GroupOfEmployeesIn groupOfEmployeesIn, @PathVariable Long id) {
        return ResponseEntity.ok(groupOfEmployeesService.updateGroup(groupOfEmployeesIn, id));
    }


    @PostMapping(path = "/modifierMembers/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<GroupOfEmployeesOut> modifierMembers(@RequestBody List<Long> groupOfEmployeesIn, @PathVariable Long id) {
        GroupOfEmployeesOut groupOfEmployeesOut = groupOfEmployeesService.modifierMembers(groupOfEmployeesIn, id);
        return groupOfEmployeesOut == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(groupOfEmployeesOut);
    }
}
