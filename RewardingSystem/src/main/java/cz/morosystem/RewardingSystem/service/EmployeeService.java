package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Role;
import cz.morosystem.RewardingSystem.model.in.EmployeeIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    final
    EmployeeRepository employeeRepository;
    final
    ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<EmployeeOut> getAllEmployees() {
        List<Employee> dbEmployees = employeeRepository.findAll();
        return dbEmployees.stream().map(m -> modelMapper.map(m, EmployeeOut.class)).toList();
    }

    public EmployeeOut getOutEmployee(Long id) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        return dbEmployee.isPresent() ? modelMapper.map(dbEmployee, EmployeeOut.class) : null;
    }

    //NOT NEEDED
//    public EmployeeOut getOutEmployee(String sub) {
//        Optional<Employee> dbEmployee = employeeRepository.findEmployeeBySub(sub);
//        return dbEmployee.isPresent() ? modelMapper.map(dbEmployee, EmployeeOut.class) : null;
//    }

    public Employee getEmployee(Long id) {
        Optional<Employee> dbEmployee = employeeRepository.findById(id);
        return dbEmployee.orElse(null);
    }

    public Employee getEmployee(String sub) {
        Optional<Employee> dbEmployee = employeeRepository.findEmployeeBySub(sub);
        return dbEmployee.orElse(null);
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

    public List<Role> getMyRoles(String sub) {
        Employee employee = getEmployee(sub);
        return employee == null ? new ArrayList<>() : employee.getRoles();
    }

    //NOT NEEDED
//    public EmployeeOut createEmployee(EmployeeIn employeeIn) {
//        Employee dbEmployee = modelMapper.map(employeeIn, Employee.class);
//        dbEmployee.setRoles(List.of(Role.ROLE_USER));
//        return modelMapper.map(employeeRepository.save(dbEmployee), EmployeeOut.class);
//    }

    public EmployeeOut updateEmployee(Long id, EmployeeIn employeeIn) {
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

    public void loginEmployee(OAuth2AuthenticatedPrincipal oAuth2User) {
        Optional<Employee> dbEmployee = employeeRepository.findEmployeeBySub(oAuth2User.getAttribute("sub"));
        if (dbEmployee.isEmpty()) {
            registerEmployee(oAuth2User);
        } else {
            Employee employee = dbEmployee.get();
            employee.setEmail(oAuth2User.getAttribute("email"));
            employee.setFirstName(oAuth2User.getAttribute("given_name"));
            employee.setLastName(oAuth2User.getAttribute("family_name"));
            employeeRepository.save(employee);
        }
    }

    public void registerEmployee(OAuth2AuthenticatedPrincipal oAuth2User) {
        Employee employee = new Employee();
        employee.setSub(oAuth2User.getAttribute("sub"));
        employee.setEmail(oAuth2User.getAttribute("email"));
        employee.setFirstName(oAuth2User.getAttribute("given_name"));
        employee.setLastName(oAuth2User.getAttribute("family_name"));
        employee.setRoles(List.of(Role.ROLE_USER));
        employeeRepository.save(employee);
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
