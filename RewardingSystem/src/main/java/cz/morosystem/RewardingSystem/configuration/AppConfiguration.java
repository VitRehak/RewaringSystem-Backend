package cz.morosystem.RewardingSystem.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
}
