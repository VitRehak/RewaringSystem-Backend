package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.model.in.EmployeeIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<EmployeeOut>> allEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<EmployeeOut> employee(@PathVariable Long id) {
        EmployeeOut outEmployee = employeeService.getOutEmployee(id);
        return outEmployee == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(outEmployee);
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<EmployeeOut> createEmployee(@RequestBody EmployeeIn employeeIn) {
        EmployeeOut employeeOut = employeeService.createEmployee(employeeIn);
        return ResponseEntity.ok(employeeOut);
    }

    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<EmployeeOut> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeIn) {
        EmployeeOut employeeOut = employeeService.updateEmployee(id, employeeIn);
        return employeeOut == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(employeeOut);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/modifierRoles/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<EmployeeOut> modifierRoles(@PathVariable Long id, @RequestBody List<Role> roles) {
        EmployeeOut employeeOut = employeeService.modifierRoles(id, roles);
        return employeeOut == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(employeeOut);
    }
}
