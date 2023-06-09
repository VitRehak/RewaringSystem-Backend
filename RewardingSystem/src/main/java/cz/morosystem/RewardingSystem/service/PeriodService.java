package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Period;
import cz.morosystem.RewardingSystem.model.in.PeriodIn;
import cz.morosystem.RewardingSystem.model.out.PeriodOut;
import cz.morosystem.RewardingSystem.repository.PeriodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeriodService {

    final
    PeriodRepository periodRepository;

    final
    ModelMapper modelMapper;

    public PeriodService(PeriodRepository periodRepository, ModelMapper modelMapper) {
        this.periodRepository = periodRepository;
        this.modelMapper = modelMapper;
    }

    public Period currentPeriod() {
        Optional<Period> dbPeriod = periodRepository.currentPeriod();
        return dbPeriod.orElse(null);
    }

    public PeriodOut currentPeriodOut() {
        Optional<Period> dbPeriod = periodRepository.currentPeriod();
        return dbPeriod.isPresent() ? modelMapper.map(dbPeriod, PeriodOut.class) : null;
    }

    public PeriodOut create(PeriodIn inPeriod) {
        Period period = new Period();
        period.setStartOfPeriod(inPeriod.getStartOfPeriod());
        period.setEndOfPeriod(inPeriod.getEndOfPeriod());
//        period.setStartOfPeriod(LocalDateTime.now());
//        period.setEndOfPeriod(LocalDateTime.now().plusDays(10));
        period.setBilled(false);
        return modelMapper.map(periodRepository.save(period), PeriodOut.class);
    }

    public PeriodOut billed(Long id) {
        Optional<Period> dbPeriod = periodRepository.findById(id);
        if (dbPeriod.isPresent()) {
            Period period = dbPeriod.get();
            period.setBilled(true);
            return modelMapper.map(periodRepository.save(period), PeriodOut.class);
        }
        return null;
    }
}
