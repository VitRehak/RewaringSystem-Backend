package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.GroupOfEmployeesIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.model.out.GroupOfEmployeesOut;
import cz.morosystem.RewardingSystem.service.GroupOfEmployeesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/GroupOfEmployees")
public class GroupOfEmployeesController {

    @Autowired
    GroupOfEmployeesService groupOfEmployeesService;


    //MY GROUPS
    @GetMapping(path = "/myGroups", produces = "application/json")
    public ResponseEntity<List<GroupOfEmployeesOut>> myGroups(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(groupOfEmployeesService.getMyGroups(principal.getAttribute("sub")));
    }

    //CREATE
    @PostMapping(path = "/Create", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<GroupOfEmployeesOut> createGroup(@RequestBody GroupOfEmployeesIn groupOfEmployeesIn, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(groupOfEmployeesService.create(groupOfEmployeesIn, principal.getAttribute("sub")));
    }

    //DELETE
    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    @PreAuthorize("@permissionEvaluator.groupOwner(principal, #id)")
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        groupOfEmployeesService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    //UPDATE
    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.groupOwner(principal, #id)")
    public ResponseEntity<GroupOfEmployeesOut> updateGroup(@RequestBody GroupOfEmployeesIn groupOfEmployeesIn, @PathVariable Long id) {
        return ResponseEntity.ok(groupOfEmployeesService.updateGroup(groupOfEmployeesIn, id));
    }

    //CHANGE MEMBERS
    @PostMapping(path = "/modifierMembers/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.groupOwner(principal, #id)")
    public ResponseEntity<GroupOfEmployeesOut> modifierMembers(@RequestBody List<Long> groupOfEmployeesIn, @PathVariable Long id, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        GroupOfEmployeesOut groupOfEmployeesOut = groupOfEmployeesService.modifierMembers(groupOfEmployeesIn, id, principal.getAttribute("sub"));
        return groupOfEmployeesOut == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(groupOfEmployeesOut);
    }


    //////////////////////////////NOT NEEDED NOW////////////////////////////////////////


    @GetMapping(path = "/groupMembers/{id}", produces = "application/json")
    public ResponseEntity<List<EmployeeOut>> groupMembers(@PathVariable Long id) {
        List<EmployeeOut> employeeOuts = groupOfEmployeesService.getGroupMembers(id);
        return employeeOuts == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employeeOuts);
    }
}
