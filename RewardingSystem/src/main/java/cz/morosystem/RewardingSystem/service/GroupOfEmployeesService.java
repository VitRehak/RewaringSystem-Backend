package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.in.GroupOfEmployeesIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.model.out.GroupOfEmployeesOut;
import cz.morosystem.RewardingSystem.repository.GroupOfEmployeesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupOfEmployeesService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    GroupOfEmployeesRepository groupOfEmployeesRepository;

    public List<GroupOfEmployeesOut> getMyGroups(Long id) {
        List<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findAllGroupsOfEmployee(id);
        return dbGroupOfEmployees.stream().map(g -> modelMapper.map(g, GroupOfEmployeesOut.class)).toList();
    }

    public GroupOfEmployeesOut create(GroupOfEmployeesIn groupOfEmployeesIn, Long id) {
        GroupOfEmployees groupOfEmployees = modelMapper.map(groupOfEmployeesIn, GroupOfEmployees.class);
        Employee creator = employeeService.getEmployee(id);
        groupOfEmployees.setCreator(creator);
        return modelMapper.map(groupOfEmployeesRepository.save(groupOfEmployees), GroupOfEmployeesOut.class);
    }

    public void deleteGroup(Long id) {
        groupOfEmployeesRepository.deleteById(id);
    }

    public GroupOfEmployeesOut updateGroup(GroupOfEmployeesIn groupOfEmployeesIn, Long id) {
        Optional<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findById(id);
        if (dbGroupOfEmployees.isPresent()) {
            GroupOfEmployees groupOfEmployees = dbGroupOfEmployees.get();
            groupOfEmployees.setName(groupOfEmployeesIn.getName());
            groupOfEmployees.setDescription(groupOfEmployeesIn.getDescription());
            return modelMapper.map(groupOfEmployeesRepository.save(groupOfEmployees), GroupOfEmployeesOut.class);
        }
        return null;

    }

    public GroupOfEmployeesOut modifierMembers(List<Long> groupOfEmployeesIn, Long id) {
        Optional<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findById(id);
        if (dbGroupOfEmployees.isPresent()) {
            GroupOfEmployees groupOfEmployees = dbGroupOfEmployees.get();
            groupOfEmployeesIn.removeIf(g -> g.equals(id));
            List<Employee> employees = employeeService.getListOfEmployees(groupOfEmployeesIn);
            groupOfEmployees.setMembers(employees);
            return modelMapper.map(groupOfEmployeesRepository.save(groupOfEmployees), GroupOfEmployeesOut.class);

        }
        return null;
    }

    public List<EmployeeOut> getGroupMembers(Long id) {
        Optional<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findById(id);
        if (dbGroupOfEmployees.isPresent()) {
            GroupOfEmployees groupOfEmployees = dbGroupOfEmployees.get();
            List<Employee> employees = groupOfEmployees.getMembers();
            return employees.stream().map(e -> modelMapper.map(e, EmployeeOut.class)).toList();
        }
        return null;

    }
}
