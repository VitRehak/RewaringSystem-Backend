package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.PeriodState;
import cz.morosystem.RewardingSystem.model.in.PeriodStateIn;
import cz.morosystem.RewardingSystem.model.out.PeriodStateOut;
import cz.morosystem.RewardingSystem.service.PeriodStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/periodState")
public class PeriodStateController {

    @Autowired
    PeriodStateService periodStateService;

    @PostMapping(path = "/assignBudget/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PeriodStateOut> assignBudget(@RequestBody PeriodStateIn periodStateIn, @PathVariable Long id){
        return ResponseEntity.ok(periodStateService.assignBudget(periodStateIn,id ));
    }

    ///////////////////////MISSING USER ID---PATH ID IS SUBSTITUTION///////////////////////////////////
    @GetMapping(path = "/myBudget/{id}", produces ="application/json" )
    public ResponseEntity<PeriodStateOut> getMyBudget(@PathVariable Long id){
        return ResponseEntity.ok(periodStateService.getMyBudget(id));
    }
}
