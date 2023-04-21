package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.PeriodIn;
import cz.morosystem.RewardingSystem.model.out.PeriodOut;
import cz.morosystem.RewardingSystem.service.PeriodService;
import jakarta.transaction.Transactional;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "period")
public class PeriodController {

    @Autowired
    PeriodService periodService;

    //CURRENT PERIOD
    @GetMapping(path = "/currentPeriod", produces = "application/json")
    public ResponseEntity<PeriodOut> getCurrentPeriod() {
        PeriodOut periodOut = periodService.currentPeriodOut();
        return periodOut == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(periodOut);
    }

    //CREATE PERIOD
    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<PeriodOut> create(@RequestBody PeriodIn inPeriod) {
        return ResponseEntity.ok(periodService.create(inPeriod));
    }

    //CHANGE PERIOD STATE
    @PutMapping(path = "/billed/{id}")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<PeriodOut> billed(@PathVariable Long id) {
        PeriodOut period = periodService.billed(id);
        return period == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(period);
    }
}
