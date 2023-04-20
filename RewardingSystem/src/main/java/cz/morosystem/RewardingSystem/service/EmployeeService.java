package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.model.in.EmployeeIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<EmployeeOut> getAllEmployees() {
        List<Employee> dbEmployees = employeeRepository.findAll();
        return dbEmployees.stream().map(m -> modelMapper.map(m, EmployeeOut.class)).toList();
    }

    public EmployeeOut getOutEmployee(Long id) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        return dbEmployee.isPresent() ? modelMapper.map(dbEmployee, EmployeeOut.class) : null;
    }

    public Employee getEmployee(Long id) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        return dbEmployee.isPresent() ? dbEmployee.get() : null;
    }

    public void loginEmployee(OAuth2User oAuth2User) {
        Optional<Employee> dbEmployee = employeeRepository.findEmployeeBySub(oAuth2User.getAttribute("sub"));
        if (dbEmployee.isEmpty())
            registerEmployee(oAuth2User);

    }

    public void registerEmployee(OAuth2User oAuth2User) {
        Employee employee = new Employee();
        employee.setSub(oAuth2User.getAttribute("sub"));
        employee.setEmail(oAuth2User.getAttribute("email"));
        employee.setFirstName(oAuth2User.getAttribute("given_name"));
        employee.setLastName(oAuth2User.getAttribute("family_name"));
        employee.setRoles(List.of(Role.ROLE_USER));
        employeeRepository.save(employee);
    }


    public EmployeeOut createEmployee(EmployeeIn employeeIn) {
        Employee dbEmployee = modelMapper.map(employeeIn, Employee.class);
        dbEmployee.setRoles(List.of(Role.ROLE_USER));
        dbEmployee = employeeRepository.save(dbEmployee);
        return modelMapper.map(dbEmployee, EmployeeOut.class);
    }

    public EmployeeOut updateEmployee(Long id, Employee employeeIn) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        if (dbEmployee.isPresent()) {
            Employee employee = dbEmployee.get();
            employee.setFirstName(employeeIn.getFirstName());
            employee.setLastName(employeeIn.getLastName());
            return modelMapper.map(employeeRepository.save(employee), EmployeeOut.class);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeOut modifierRoles(Long id, List<Role> roles) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        if (dbEmployee.isPresent()) {
            Employee employee = dbEmployee.get();
            employee.setRoles(roles);
            return modelMapper.map(employeeRepository.save(employee), EmployeeOut.class);
        }
        return null;
    }

    public List<Employee> getListOfEmployees(List<Long> inEmployees) {
        List<Employee> outEmployees = new ArrayList<>();
        inEmployees.forEach(e -> {
            Optional<Employee> employee = employeeRepository.findById(e);
            employee.ifPresent(outEmployees::add);
        });
        return outEmployees;
    }
}
