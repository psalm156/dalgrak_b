package springbootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("springbootApplication.domain") 
@EnableJpaRepositories("springbootApplication.repository")

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
