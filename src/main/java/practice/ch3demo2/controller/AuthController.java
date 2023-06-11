package practice.ch3demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.ch3demo2.entity.Otp;
import practice.ch3demo2.entity.User;
import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.service.JpaUserDetailsManager;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private JpaUserDetailsManager jpaUserDetailsManager;

    // http POST http://localhost:8080/user/add  Content-Type:application/json  username=sammy  password=1234
    @PostMapping("/user/add")
    public void addUser(@RequestBody User user) {

        SecurityUser securityUser = new SecurityUser(user);

        jpaUserDetailsManager.createUser(securityUser);
    }

    // http POST http://localhost:8080/user/auth  Content-Type:application/json  username=sammy  password=1234
    @PostMapping("/user/auth")
    public void auth(@RequestBody User user) {

        SecurityUser securityUser = new SecurityUser(user);

        jpaUserDetailsManager.authenticateUser(securityUser);
    }

    // http --verbose POST http://localhost:8080/otp/check  Content-Type:application/json  username=sammy  otpCode={OTP for `sammy`}
    @PostMapping("/otp/check")
    public void check(@RequestBody Otp otp, HttpServletResponse response) {

        if (jpaUserDetailsManager.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}