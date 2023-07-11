package cz.morosystem.RewardingSystem.service;

import cz.morosystem.RewardingSystem.model.entity.PredefinedMessage;
import cz.morosystem.RewardingSystem.model.in.PredefinedMessageIn;
import cz.morosystem.RewardingSystem.model.out.PredefinedMessageOut;
import cz.morosystem.RewardingSystem.repository.PredefinedMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedMessageService {

    final
    PredefinedMessageRepository predefinedMessageRepository;

    final
    ModelMapper modelMapper;

    public PredefinedMessageService(PredefinedMessageRepository predefinedMessageRepository, ModelMapper modelMapper) {
        this.predefinedMessageRepository = predefinedMessageRepository;
        this.modelMapper = modelMapper;
    }

    public List<PredefinedMessageOut> getAllPredefinedMessage() {
        List<PredefinedMessage> dbPredefinedMessages = predefinedMessageRepository.findAll();
        return dbPredefinedMessages.stream().map(m -> modelMapper.map(m, PredefinedMessageOut.class)).toList();
    }

    public PredefinedMessageOut getPredefinedMessage(Long id) {
        Optional<PredefinedMessage> dbPredefinedMessage = predefinedMessageRepository.findById(id);
        return dbPredefinedMessage.isPresent() ? modelMapper.map(dbPredefinedMessage, PredefinedMessageOut.class) : null;
    }

    public PredefinedMessageOut addPredefinedMessage(PredefinedMessageIn predefinedMessageIn) {
        PredefinedMessage predefinedMessage = predefinedMessageRepository.save(modelMapper.map(predefinedMessageIn, PredefinedMessage.class));
        return modelMapper.map(predefinedMessage, PredefinedMessageOut.class);
    }

    public void deletePredefinedMessage(Long id) {
        predefinedMessageRepository.deleteById(id);
    }

    public PredefinedMessageOut updatePredefinedMessage(PredefinedMessageIn predefinedMessageIn, Long id) {
        Optional<PredefinedMessage> dbPredefinedMessage = predefinedMessageRepository.findById(id);
        if (dbPredefinedMessage.isPresent()) {
            PredefinedMessage predefinedMessage = dbPredefinedMessage.get();
            predefinedMessage.setText(predefinedMessageIn.getText());
            return modelMapper.map(predefinedMessageRepository.save(predefinedMessage), PredefinedMessageOut.class);
        } else {
            return null;
        }
    }
}
