package mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"mvc", "core"})
public class MvcMemberPortal {

	public static void main(String[] args) {
		SpringApplication.run(MvcMemberPortal.class, args);
	}
}
