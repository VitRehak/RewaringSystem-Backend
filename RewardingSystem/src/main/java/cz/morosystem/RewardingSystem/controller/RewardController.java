package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.RewardIn;
import cz.morosystem.RewardingSystem.model.out.RewardOut;
import cz.morosystem.RewardingSystem.service.RewardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
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
        RewardOut outReward = rewardService.getRewardOut(id);
        return outReward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(outReward);
    }

    @GetMapping(path = "/myRewards")
    public ResponseEntity<List<RewardOut>> getAllMyRewards( @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(rewardService.getAllMyRewards(principal.getAttribute("sub")));
    }

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> create(@RequestBody RewardIn rewardIn, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(rewardService.createReward(rewardIn, principal.getAttribute("sub")));
    }

    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> update(@RequestBody RewardIn rewardIn, @PathVariable Long id) {
        RewardOut reward = rewardService.update(rewardIn, id);
        return reward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(reward);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<RewardOut> delete(@PathVariable Long id) {
        rewardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
