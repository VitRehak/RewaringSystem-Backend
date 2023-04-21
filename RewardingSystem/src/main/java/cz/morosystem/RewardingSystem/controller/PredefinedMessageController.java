package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.entity.PredefinedMessage;
import cz.morosystem.RewardingSystem.model.in.PredefinedMessageIn;
import cz.morosystem.RewardingSystem.model.out.EmployeeOut;
import cz.morosystem.RewardingSystem.model.out.PredefinedMessageOut;
import cz.morosystem.RewardingSystem.service.PredefinedMessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/predefinedMessage")
public class PredefinedMessageController {

    @Autowired
    PredefinedMessageService predefinedMessageService;

    //ALL
    @GetMapping(path = "/all")
    public ResponseEntity<List<PredefinedMessageOut>> getAll() {
        return ResponseEntity.ok(predefinedMessageService.getAllPredefinedMessage());
    }

    //DETAIL
    @GetMapping(path = "/{id}")
    public ResponseEntity<PredefinedMessageOut> getPredefinedMessage(@PathVariable Long id) {
        PredefinedMessageOut predefinedMessageOut = predefinedMessageService.getPredefinedMessage(id);
        return predefinedMessageOut ==   null ? ResponseEntity.notFound().build() : ResponseEntity.ok(predefinedMessageOut);
    }

    //CREATE
    @PostMapping(path = "/add")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<PredefinedMessageOut> addMessage(@RequestBody PredefinedMessageIn predefinedMessageIn) {
        return ResponseEntity.ok(predefinedMessageService.addPredefinedMessage(predefinedMessageIn));
    }

    //DELETE
    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    @PreAuthorize("@permissionEvaluator.adminRole(principal)")
    public ResponseEntity<PredefinedMessageOut> deleteMessage(@PathVariable Long id) {
        predefinedMessageService.deletePredefinedMessage(id);
        return ResponseEntity.noContent().build();
    }
}
