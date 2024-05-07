package carmax.version001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("carmax.version001.repository")
@EntityScan("carmax.version001.model")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
