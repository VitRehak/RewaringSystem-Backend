package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import cz.morosystem.RewardingSystem.model.in.PeriodStateIn;
import cz.morosystem.RewardingSystem.model.out.PeriodOut;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.service.PeriodStateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/periodState")
public class PeriodStateController {

    @Autowired
    PeriodStateService periodStateService;

    //ASSIGN BUDGET
    @PostMapping(path = "/assignBudget", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<List<PeriodStateOut>> assignBudget(@RequestBody List<PeriodStateIn> periodStateIn){
        return ResponseEntity.ok(periodStateService.assignBudget(periodStateIn));
    }

    //MY BUDGET
    @GetMapping(path = "/myBudget", produces ="application/json" )
    public ResponseEntity<PeriodStateOut> getMyBudget(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal){
        return ResponseEntity.ok(periodStateService.getMyBudget(principal.getAttribute("sub")));
    }
}
