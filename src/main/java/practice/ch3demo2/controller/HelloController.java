package practice.ch3demo2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication a) {

        return "Hello " + a.getName() + ", Welcome to Spring Security with the integration of JPA :)";
    }
}