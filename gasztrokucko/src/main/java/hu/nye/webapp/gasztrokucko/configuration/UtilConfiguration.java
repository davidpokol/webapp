package hu.nye.webapp.gasztrokucko.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class UtilConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
