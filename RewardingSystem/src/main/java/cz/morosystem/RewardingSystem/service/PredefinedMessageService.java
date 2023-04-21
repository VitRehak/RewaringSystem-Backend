package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.PredefinedMessage;
import cz.morosystem.RewardingSystem.model.in.PredefinedMessageIn;
import cz.morosystem.RewardingSystem.model.out.PredefinedMessageOut;
import cz.morosystem.RewardingSystem.repository.PredefinedMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedMessageService {

    @Autowired
    PredefinedMessageRepository predefinedMessageRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<PredefinedMessageOut> getAllPredefinedMessage() {
        List<PredefinedMessage> dbPredefinedMessages = predefinedMessageRepository.findAll();
        return dbPredefinedMessages.stream().map(m -> modelMapper.map(m, PredefinedMessageOut.class)).toList();
    }

    public PredefinedMessageOut addPredefinedMessage(PredefinedMessageIn predefinedMessageIn) {
        PredefinedMessage predefinedMessage = predefinedMessageRepository.save(modelMapper.map(predefinedMessageIn, PredefinedMessage.class));
        return modelMapper.map(predefinedMessage, PredefinedMessageOut.class);
    }

    public void deletePredefinedMessage(Long id) {
        predefinedMessageRepository.deleteById(id);
    }

    public PredefinedMessageOut getPredefinedMessage(Long id) {
        Optional<PredefinedMessage> dbPredefinedMessage = predefinedMessageRepository.findById(id);


        return dbPredefinedMessage.isPresent() ? modelMapper.map(dbPredefinedMessage, PredefinedMessageOut.class) : null;
    }
}
