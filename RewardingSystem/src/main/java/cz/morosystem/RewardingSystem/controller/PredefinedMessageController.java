package cz.morosystem.RewardingSystem.controller;

import cz.morosystem.RewardingSystem.model.in.PredefinedMessageIn;
import cz.morosystem.RewardingSystem.model.out.PredefinedMessageOut;
import cz.morosystem.RewardingSystem.service.PredefinedMessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/predefinedMessage")
public class PredefinedMessageController {

    @Autowired
    PredefinedMessageService predefinedMessageService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<PredefinedMessageOut>> getAll() {
        return ResponseEntity.ok(predefinedMessageService.getAllPredefinedMessage());
    }

    @PostMapping(path = "/add")
    @Transactional
    public ResponseEntity<PredefinedMessageOut> addMessage(@RequestBody PredefinedMessageIn predefinedMessageIn) {
        return ResponseEntity.ok(predefinedMessageService.addPredefinedMessage(predefinedMessageIn));
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity<PredefinedMessageOut> addMessage(@PathVariable Long id) {
        predefinedMessageService.deletePredefinedMessage(id);
        return ResponseEntity.noContent().build();
    }
}
