package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import cz.morosystem.RewardingSystem.model.in.PeriodStateIn;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.repository.PeriodStateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodStateService {

    @Autowired
    PeriodStateRepository periodStateRepository;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PeriodService periodService;
    @Autowired
    ModelMapper modelMapper;

    public List<PeriodStateOut> assignBudget(List<PeriodStateIn> periodStateIn, Long id) {
        List<PeriodStateOut> periodStateOut = new ArrayList<>();
        periodStateIn.forEach(record -> {
            PeriodState periodState = new PeriodState();
            periodState.setBudget(record.getBudget());
            periodState.setEmployeeId(record.getEmployeeId());
            periodState.setPeriodId(id);
            periodStateOut.add(modelMapper.map(periodStateRepository.save(periodState), PeriodStateOut.class));
        });
        return periodStateOut;
    }

    public PeriodStateOut getMyBudget(String sub) {
        PeriodStateOut periodStateOut;
        Employee employee = employeeService.getEmployee(sub);
        Long idPeriod = periodService.currentPeriod().getId();
        Optional<PeriodState> periodStateDb = periodStateRepository.findByEmployeeAndPeriod(employee.getId(), idPeriod);
        if (periodStateDb.isPresent()) {
            PeriodState periodState = periodStateDb.get();
            periodStateOut = modelMapper.map(periodState, PeriodStateOut.class);
        } else {
            PeriodState periodState = new PeriodState();
            periodState.setEmployeeId(employee.getId());
            periodState.setBudget(0);
            periodState.setPeriodId(periodService.currentPeriod().getId());
            periodStateOut = modelMapper.map(periodStateRepository.save(periodState), PeriodStateOut.class);
        }
        return periodStateOut;
    }

    public void updateStateBudget(int budget, String sub) {
        Employee employee = employeeService.getEmployee(sub);
        Long idPeriod = periodService.currentPeriod().getId();
        Optional<PeriodState> periodStateDb = periodStateRepository.findByEmployeeAndPeriod(employee.getId(), idPeriod);
        if (periodStateDb.isPresent()) {
            PeriodState periodState = periodStateDb.get();
            periodState.setBudget(budget);
        }
        else {
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
