package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.Period;
import cz.morosystem.RewardingSystem.model.entity.Reward;
import cz.morosystem.RewardingSystem.model.in.RewardIn;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.model.out.RewardOut;
import cz.morosystem.RewardingSystem.repository.RewardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RewardRepository rewardRepository;

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PeriodService periodService;
    @Autowired
    PeriodStateService periodStateService;

    public List<RewardOut> getAllRewards() {
        List<Reward> dbReward = rewardRepository.findAll();
        return dbReward.stream().map(r -> modelMapper.map(r, RewardOut.class)).toList();
    }

    public RewardOut getRewardOut(Long id) {
        Optional<Reward> dbReward = rewardRepository.findById(id);
        return dbReward.isPresent() ? modelMapper.map(dbReward.get(), RewardOut.class) : null;
    }

    public Reward getReward(Long id) {
        Optional<Reward> dbReward = rewardRepository.findById(id);
        return dbReward.isPresent() ? dbReward.get() : null;
    }

    public List<RewardOut> getAllMyRewards(String sub) {
        Employee employee = employeeService.getEmployee(sub);
        List<Reward> dbRewards = rewardRepository.findAllSendersRewards(employee.getId());
        return dbRewards.stream().map(r -> modelMapper.map(r, RewardOut.class)).toList();
    }

    public RewardOut createReward(RewardIn rewardIn, String sub) {
        PeriodStateOut state = periodStateService.getMyBudget(sub);
        if (state.getBudget() - rewardIn.getAmountOfMoney() < 0) {
        } else {
            return null;
        }
        Period period = periodService.currentPeriod();
        Employee sender = employeeService.getEmployee(sub);
        Employee receiver = employeeService.getEmployee(rewardIn.getReceiver());
        Reward reward = new Reward();
        reward.setReceiver(receiver);
        reward.setSender(sender);
        reward.setAmountOfMoney(rewardIn.getAmountOfMoney());
        reward.setText(rewardIn.getText());
        reward.setDraft(rewardIn.isDraft());
        reward.setPeriod(period);
        periodStateService.updateStateBudget(state.getBudget() - rewardIn.getAmountOfMoney(), sub);
        return modelMapper.map(rewardRepository.save(reward), RewardOut.class);
    }

    public RewardOut update(RewardIn rewardIn, Long id) {
        Employee receiver = employeeService.getEmployee(rewardIn.getReceiver());
        Optional<Reward> dbReward = rewardRepository.findById(id);
        if (dbReward.isPresent()) {
            Reward reward = dbReward.get();
            reward.setReceiver(receiver);
            reward.setAmountOfMoney(rewardIn.getAmountOfMoney());
            reward.setText(rewardIn.getText());
            reward.setDraft(rewardIn.isDraft());
            return modelMapper.map(rewardRepository.save(reward), RewardOut.class);
        }
        return null;
    }

    public void delete(Long id) {
        rewardRepository.deleteById(id);
    }
}
