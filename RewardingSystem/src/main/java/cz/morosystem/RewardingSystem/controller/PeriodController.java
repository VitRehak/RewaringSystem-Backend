package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.PeriodIn;
import cz.morosystem.RewardingSystem.model.out.PeriodOut;
import cz.morosystem.RewardingSystem.service.PeriodService;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "period")
public class PeriodController {

    @Autowired
    PeriodService periodService;

    @GetMapping(path = "currentPeriod", produces = "application/json")
    public ResponseEntity<PeriodOut> getCurrentPeriod() {
        return ResponseEntity.ok(periodService.currentPeriodOut());
    }

    @PostMapping(path = "create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PeriodOut> create(@RequestBody PeriodIn inPeriod) {
        return ResponseEntity.ok(periodService.create(inPeriod));
    }

    @PutMapping(path = "billed/{id}")
    public ResponseEntity<PeriodOut> billed(@PathVariable Long id) {
        PeriodOut period = periodService.billed(id);
        return period == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(period);
    }
}
