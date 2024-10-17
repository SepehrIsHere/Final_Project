package ir.maktabsharif.finalproject.util;

import com.github.javafaker.Faker;
import ir.maktabsharif.finalproject.service.PaymentService;
import ir.maktabsharif.finalproject.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.session.StandardSession;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

    @Bean
    ValidationUtil validationUtil() {
        return new ValidationUtil();
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
