package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Period;
import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import cz.morosystem.RewardingSystem.model.in.PeriodStateIn;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.repository.PeriodStateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodStateService {

    final
    PeriodStateRepository periodStateRepository;
    final
    EmployeeService employeeService;
    final
    PeriodService periodService;
    final
    ModelMapper modelMapper;

    public PeriodStateService(PeriodStateRepository periodStateRepository, EmployeeService employeeService, PeriodService periodService, ModelMapper modelMapper) {
        this.periodStateRepository = periodStateRepository;
        this.employeeService = employeeService;
        this.periodService = periodService;
        this.modelMapper = modelMapper;
    }

    public List<PeriodStateOut> assignBudget(List<PeriodStateIn> periodStateIn) {
        Period period = periodService.currentPeriod();
        List<PeriodStateOut> periodStateOut = new ArrayList<>();
        periodStateIn.forEach(record -> {
            PeriodState periodState = new PeriodState();
            periodState.setBudget(record.getBudget());
            periodState.setEmployeeId(record.getEmployeeId());
            periodState.setPeriodId(period.getId());
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
            return periodStateOut;
        } else {
            return null;
        }
    }
    public void updateStateBudget(int budget, String sub) {
        Employee employee = employeeService.getEmployee(sub);
        Long idPeriod = periodService.currentPeriod().getId();
        Optional<PeriodState> periodStateDb = periodStateRepository.findByEmployeeAndPeriod(employee.getId(), idPeriod);
        if (periodStateDb.isPresent()) {
            PeriodState periodState = periodStateDb.get();
            periodState.setBudget(budget);
        }
    }
}
