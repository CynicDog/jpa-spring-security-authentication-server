package practice.ch3demo2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoReadAuthController {

    @GetMapping("/no-read-auth")
    public String noAuth(Authentication a) {

        return "Sorry " + a.getName() + ", you don't have permission to read.";
    }
}
