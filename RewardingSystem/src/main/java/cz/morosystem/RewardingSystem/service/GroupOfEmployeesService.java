package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.in.GroupOfEmployeesIn;
import cz.morosystem.RewardingSystem.model.out.GroupOfEmployeesOut;
import cz.morosystem.RewardingSystem.repository.GroupOfEmployeesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupOfEmployeesService {

    final
    ModelMapper modelMapper;
    final
    EmployeeService employeeService;
    final
    GroupOfEmployeesRepository groupOfEmployeesRepository;

    public GroupOfEmployeesService(ModelMapper modelMapper, EmployeeService employeeService, GroupOfEmployeesRepository groupOfEmployeesRepository) {
        this.modelMapper = modelMapper;
        this.employeeService = employeeService;
        this.groupOfEmployeesRepository = groupOfEmployeesRepository;
    }


    public List<GroupOfEmployeesOut> getMyGroups(String sub) {
        Employee employee = employeeService.getEmployee(sub);
        List<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findAllGroupsOfEmployee(employee.getId());
        return dbGroupOfEmployees.stream().map(g -> modelMapper.map(g, GroupOfEmployeesOut.class)).toList();
    }

    public GroupOfEmployeesOut create(GroupOfEmployeesIn groupOfEmployeesIn, String sub) {
        GroupOfEmployees groupOfEmployees = modelMapper.map(groupOfEmployeesIn, GroupOfEmployees.class);
        Employee creator = employeeService.getEmployee(sub);
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

    public GroupOfEmployeesOut modifierMembers(List<Long> groupOfEmployeesIn, Long id, String sub) {
        Employee employee = employeeService.getEmployee(sub);
        Optional<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findById(id);
        if (dbGroupOfEmployees.isPresent()) {
            GroupOfEmployees groupOfEmployees = dbGroupOfEmployees.get();
            groupOfEmployeesIn.removeIf(g -> g.equals(employee.getId()));
            List<Employee> employees = employeeService.getListOfEmployees(groupOfEmployeesIn);
            groupOfEmployees.setMembers(employees);
            return modelMapper.map(groupOfEmployeesRepository.save(groupOfEmployees), GroupOfEmployeesOut.class);

        }
        return null;
    }

//    //NOT NEEDED
//    public List<Employee> getGroupMembers(Long id) {
//        Optional<GroupOfEmployees> dbGroupOfEmployees = groupOfEmployeesRepository.findById(id);
//        if (dbGroupOfEmployees.isPresent()) {
//            GroupOfEmployees groupOfEmployees = dbGroupOfEmployees.get();
//            return groupOfEmployees.getMembers();
//        }
//        return null;
//    }

//    //NOT NEEDED
//    public List<EmployeeOut> getGroupMembersOut(Long id) {
//        return getGroupMembers(id).stream().map(e -> modelMapper.map(e, EmployeeOut.class)).toList();
//    }

    public GroupOfEmployees getGroup(Long id) {
        Optional<GroupOfEmployees> dbGroupOfEmployee = groupOfEmployeesRepository.findById(id);
        return dbGroupOfEmployee.orElse(null);

    }
}
