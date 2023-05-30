package practice.ch3demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() { return "Hello, Spring Security with the integration of JPA :)"; }
}
