package ir.maktabsharif.finalproject;

import ir.maktabsharif.finalproject.repository.CustomerRepository;
import ir.maktabsharif.finalproject.service.TaskService;
import ir.maktabsharif.finalproject.util.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class FinalprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalprojectApplication.class, args);
	}

}
