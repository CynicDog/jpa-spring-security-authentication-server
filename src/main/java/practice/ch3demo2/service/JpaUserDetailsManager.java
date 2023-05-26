package practice.ch3demo2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.model.User;
import practice.ch3demo2.repository.UserRepository;

import static practice.ch3demo2.model.SecurityUser.toAuthorities;

@Component
public class JpaUserDetailsManager implements UserDetailsManager {

    private UserRepository userRepository;

    public JpaUserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new SecurityUser(user);
    }

    @Override
    public void createUser(UserDetails userDetails) {

        User user = new User(
                userDetails.getUsername(),
                userDetails.getPassword(),
                toAuthorities(userDetails.getUsername(), userDetails.getAuthorities()));

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user != null) {
            user.setPassword(userDetails.getPassword());
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(String s) {

        User user = userRepository.findByUsername(s);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public void changePassword(String s, String s1) {

        User user = userRepository.findByUsername(s);

        if (user != null) {
            user.setPassword(s1);
            userRepository.save(user);
        }
    }

    @Override
    public boolean userExists(String username) {

        User user = userRepository.findByUsername(username);

        return user != null;
    }
}
