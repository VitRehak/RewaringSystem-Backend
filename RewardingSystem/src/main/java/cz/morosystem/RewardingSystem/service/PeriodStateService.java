package cz.morosystem.RewardingSystem.service;

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

    public PeriodStateOut getMyBudget(Long idEmployee) {
        Long idPeriod = periodService.currentPeriod().getId();
        Optional<PeriodState> periodStateDb = periodStateRepository.findByEmployeeAndPeriod(idEmployee,idPeriod);
        if (periodStateDb.isPresent()){
            PeriodState periodState = periodStateDb.get();
            return modelMapper.map(periodState,PeriodStateOut.class);
        }
        else{
            PeriodStateOut periodStateOut = new PeriodStateOut();
            periodStateOut.setEmployeeId(idEmployee);
            periodStateOut.setBudget(0);
            periodStateOut.setPeriodId(periodService.currentPeriod().getId());
            return periodStateOut;
        }
    }
}
