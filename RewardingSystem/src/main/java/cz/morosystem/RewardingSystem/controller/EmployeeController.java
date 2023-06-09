package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.model.in.EmployeeIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //ALL
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<EmployeeOut>> getAll() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    //DETAIL
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<EmployeeOut> getEmployee(@PathVariable Long id) {
        EmployeeOut outEmployee = employeeService.getOutEmployee(id);
        return outEmployee == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(outEmployee);
    }

    //ROLES
    @GetMapping(path = "/myRoles", produces = "application/json")
    public ResponseEntity<List<Role>> getMyRoles(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        List<Role> roles = employeeService.getMyRoles(principal.getAttribute("sub"));
        return ResponseEntity.ok(roles);
    }

    //ROLE CHANGE
    @PostMapping(path = "/modifierRoles/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<EmployeeOut> modifierRoles(@PathVariable Long id, @RequestBody List<Role> roles) {
        EmployeeOut employeeOut = employeeService.modifierRoles(id, roles);
        return employeeOut == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employeeOut);
    }

    //////////////////////////////NOT NEEDED NOW////////////////////////////////////////

//    //CREATE
//    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
//    @Transactional
//    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
//    public ResponseEntity<EmployeeOut> createEmployee(@RequestBody EmployeeIn employeeIn) {
//        EmployeeOut employeeOut = employeeService.createEmployee(employeeIn);
//        return employeeOut == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(employeeOut);
//    }

    //UPDATE
    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<EmployeeOut> updateEmployee(@PathVariable Long id, @RequestBody EmployeeIn employeeIn) {
        EmployeeOut employeeOut = employeeService.updateEmployee(id, employeeIn);
        return employeeOut == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(employeeOut);
    }

    //DELETE
    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
