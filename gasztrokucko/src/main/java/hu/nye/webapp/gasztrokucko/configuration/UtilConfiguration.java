package hu.nye.webapp.gasztrokucko.configuration;

import hu.nye.webapp.gasztrokucko.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public FileUtil fileUtil() { return new FileUtil(); }
}
