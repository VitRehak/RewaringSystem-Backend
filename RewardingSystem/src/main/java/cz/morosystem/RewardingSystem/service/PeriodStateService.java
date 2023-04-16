package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import cz.morosystem.RewardingSystem.model.in.PeriodStateIn;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.repository.PeriodStateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodStateService {

    @Autowired
    PeriodStateRepository periodStateRepository;
    @Autowired
    PeriodService periodService;
    @Autowired
    ModelMapper modelMapper;

    public PeriodStateOut assignBudget(PeriodStateIn periodStateIn, Long id) {
        PeriodState periodState = new PeriodState();
        periodState.setBudget(periodStateIn.getBudget());
        periodState.getId().setPeriodId(periodService.currentPeriod().getId());
        periodState.getId().setEmployeeId(id);
        return modelMapper.map(periodStateRepository.save(periodState), PeriodStateOut.class);
    }

    public PeriodStateOut getMyBudget(Long idEmployee) {
        Long idPeriod = periodService.currentPeriod().getId();
        return modelMapper.map(periodStateRepository.findByEmployeeAndPeriod(idEmployee,idPeriod),PeriodStateOut.class);
    }
}
