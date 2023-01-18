package study.spring.SpringBatchBasic;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchBasicApplication.class, args);
	}

}
