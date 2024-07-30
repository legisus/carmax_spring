package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"core", "rest", "scanner"})
public class RestPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestPortalApplication.class, args);
    }
}
