package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.RewardIn;
import cz.morosystem.RewardingSystem.model.out.RewardOut;
import cz.morosystem.RewardingSystem.service.RewardService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reward")
public class RewardController {

    final
    RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

//    //NOT NEEDED
//    //ALL
//    @GetMapping(path = "/all")
//    public ResponseEntity<List<RewardOut>> getAll() {
//        return ResponseEntity.ok(rewardService.getAllRewards());
//    }

    //DETAIL
    @GetMapping(path = "/{id}")
    public ResponseEntity<RewardOut> getReward(@PathVariable Long id) {
        RewardOut outReward = rewardService.getRewardOut(id);
        return outReward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(outReward);
    }

    //MY REWARDS
    @GetMapping(path = "/myRewards")
    public ResponseEntity<List<RewardOut>> getAllMyRewards(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        return ResponseEntity.ok(rewardService.getAllMyRewards(principal.getAttribute("sub")));
    }

    //SEND
    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> create(@RequestBody RewardIn rewardIn, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        RewardOut rewardOut = rewardService.createReward(rewardIn, principal.getAttribute("sub"));
        return rewardOut == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(rewardOut);
    }


    // OLD
//    //SEND TO EMPLOYEE
//    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
//    @Transactional
//    public ResponseEntity<RewardOut> create(@RequestBody RewardIn rewardIn, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
//        RewardOut rewardOut = rewardService.createReward(rewardIn, principal.getAttribute("sub"));
//        return rewardOut == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(rewardOut);
//    }
//
//    //SEND TO GROUP
//    @PostMapping(path = "/createMultiple", consumes = "application/json", produces = "application/json")
//    @Transactional
//    public ResponseEntity<List<RewardOut>> createMultiple(@RequestBody RewardForGroup rewardForGroup, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
//        List<RewardOut> rewardOuts = rewardService.createMultipleRewards(rewardForGroup, principal.getAttribute("sub"));
//        return ResponseEntity.ok(rewardOuts);
//    }


    //UPDATE
    @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
    @Transactional
    public ResponseEntity<RewardOut> update(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal ,@RequestBody RewardIn rewardIn, @PathVariable Long id) {
        RewardOut reward = rewardService.update(id, principal.getAttribute("sub") ,rewardIn);
        return reward == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(reward);
    }

    //DELETE
    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<RewardOut> delete(@PathVariable Long id, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        boolean deleted = rewardService.delete(id, principal.getAttribute("sub"));
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

//    //DELETE MULTIPLE
//    @DeleteMapping(path = "/deleteMultiple/{id}")
//    @Transactional
//    public ResponseEntity<RewardOut> deleteMultiple(@PathVariable Long id, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
//        rewardService.deleteMultipleRewards(id, principal.getAttribute("sub"));
//        return ResponseEntity.noContent().build();
//    }
}
