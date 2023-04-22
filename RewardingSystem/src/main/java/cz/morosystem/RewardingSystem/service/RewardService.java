package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.Employee;
import cz.morosystem.RewardingSystem.model.entity.GroupOfEmployees;
import cz.morosystem.RewardingSystem.model.entity.Period;
import cz.morosystem.RewardingSystem.model.entity.Reward;
import cz.morosystem.RewardingSystem.model.in.RewardForGroup;
import cz.morosystem.RewardingSystem.model.in.RewardIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.model.out.RewardOut;
import cz.morosystem.RewardingSystem.repository.RewardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    GroupOfEmployeesService groupOfEmployeesService;

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
        Employee sender = employeeService.getEmployee(sub);
        Employee receiver = employeeService.getEmployee(rewardIn.getReceiver());
        if (sender.equals(receiver)) {
            return null;
        }
        PeriodStateOut state = periodStateService.getMyBudget(sub);
        if (state.getBudget() - rewardIn.getAmountOfMoney() < 0) {
            return null;
        } else {
            Period period = periodService.currentPeriod();
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

    public List<RewardOut> createMultipleRewards(RewardForGroup rewardForGroup, String sub) {
        GroupOfEmployees groupOfEmployees = groupOfEmployeesService.getGroup(rewardForGroup.getReceivers());
        List<RewardOut> rewardOuts = new ArrayList<>();
        groupOfEmployees.getMembers().forEach(receiver -> {
            RewardIn rewardIn = new RewardIn();
            int numberOfReceivers = groupOfEmployees.getMembers().size();
            rewardIn.setReceiver(receiver.getId());
            rewardIn.setText(rewardForGroup.getText());
            rewardIn.setAmountOfMoney(rewardForGroup.getAmountOfMoney() / numberOfReceivers);
            rewardIn.setDraft(rewardForGroup.isDraft());
            rewardOuts.add(createReward(rewardIn, sub));
        });
        return rewardOuts;
    }

    public boolean delete(Long id, String sub) {
        PeriodStateOut state = periodStateService.getMyBudget(sub);
        Optional<Reward> dbReward = rewardRepository.findById(id);
        if (dbReward.isPresent()) {
            Reward reward = dbReward.get();
            periodStateService.updateStateBudget(reward.getAmountOfMoney() + state.getBudget(), reward.getSender().getSub());
            rewardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteMultipleRewards(Long id, String sub) {
        Employee employee = employeeService.getEmployee(sub);
        List<Employee> groupMembers = groupOfEmployeesService.getGroupMembers(id);
        List<Reward> all = rewardRepository.findAll();
        all.stream().filter(r -> (r.getSender().equals(employee) && groupMembers.contains(r.getReceiver())));
        all.forEach(r -> delete(r.getId(), sub));
    }
}









