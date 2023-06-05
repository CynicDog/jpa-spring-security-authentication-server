package practice.ch3demo2.service;

import org.jboss.logging.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import practice.ch3demo2.model.SecurityUser;
import practice.ch3demo2.entity.User;
import practice.ch3demo2.repository.UserRepository;

import static practice.ch3demo2.model.SecurityUser.toAuthorities;

@Component
public class JpaUserDetailsManager implements UserDetailsManager {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private Logger logger = Logger.getLogger(JpaUserDetailsManager.class.getName());

    public JpaUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        logger.info("Loading a user by the username.");
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
                passwordEncoder.encode(userDetails.getPassword()),
                toAuthorities(userDetails.getUsername(), userDetails.getAuthorities()));

        logger.info("Creating a user with the username of '" + user.getUsername() + "'.");
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user != null) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

            logger.info("Updating a user with the username of '" + user.getUsername() + "'.");
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(String s) {

        User user = userRepository.findByUsername(s);

        if (user != null) {

            logger.info("Deleting a user with the username of '" + user.getUsername() + "'.");
            userRepository.delete(user);
        }
    }

    @Override
    public void changePassword(String s, String s1) {

        User user = userRepository.findByUsername(s);

        if (user != null) {
            user.setPassword(passwordEncoder.encode(s1));

            logger.info("Updating a user with the username of '" + user.getUsername() + "'.");
            userRepository.save(user);
        }
    }

    @Override
    public boolean userExists(String username) {

        User user = userRepository.findByUsername(username);

        return user != null;
    }
}
