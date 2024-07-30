package rest.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, REST Portal!";
    }
}
