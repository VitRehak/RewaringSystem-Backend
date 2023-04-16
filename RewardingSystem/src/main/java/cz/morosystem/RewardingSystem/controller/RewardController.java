package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.RewardIn;
import cz.morosystem.RewardingSystem.model.out.RewardOut;
import cz.morosystem.RewardingSystem.service.RewardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reward")
public class RewardController {

    @Autowired
    RewardService rewardService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<RewardOut>> getAll() {
        return ResponseEntity.ok(rewardService.getAllRewards());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RewardOut> getReward(@PathVariable Long id) {
        RewardOut outReward = rewardService.getReward(id);
        return outReward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(outReward);
    }

    ////////////////////////MISSING USER ID---PATH ID IS SUBSTITUTION///////////////////////////////////

    @GetMapping(path = "/myRewards/{id}")
    public ResponseEntity<List<RewardOut>> getAllMyRewards(@PathVariable Long id) {
        return ResponseEntity.ok(rewardService.getAllMyRewards(id));
    }

    ////////////////////////MISSING USER ID---PATH ID IS SUBSTITUTION///////////////////////////////////

    @PostMapping(path = "create/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> create(@RequestBody RewardIn rewardIn, @PathVariable Long id) {
        return ResponseEntity.ok(rewardService.createReward(rewardIn, id));
    }

    @PutMapping(path = "update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> update(@RequestBody RewardIn rewardIn, @PathVariable Long id) {
        RewardOut reward = rewardService.update(rewardIn, id);
        return reward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(reward);
    }

    @DeleteMapping(path = "delete/{id}")
    @Transactional
    public ResponseEntity<RewardOut> delete(@PathVariable Long id) {
        rewardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
