package ir.maktabsharif.finalproject.util;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ir.maktabsharif.finalproject")
public class AppConfig {

    @Bean
    Faker faker() {
        return new Faker();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    PersonalImageUtil personalImageUtil() {
        return new PersonalImageUtil();
    }

    @Bean
    MapperUtil mapperUtil() {
        return new MapperUtil(modelMapper());
    }
}
