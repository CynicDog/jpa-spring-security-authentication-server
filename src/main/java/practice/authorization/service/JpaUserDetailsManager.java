package practice.authorization.service;

import org.jboss.logging.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import practice.authorization.entity.Otp;
import practice.authorization.model.SecurityUser;
import practice.authorization.entity.User;
import practice.authorization.repository.OtpRepository;
import practice.authorization.repository.UserRepository;
import practice.authorization.util.OneTimePasswordUtil;

import javax.transaction.Transactional;
import java.util.Optional;

import static practice.authorization.model.SecurityUser.toAuthorities;

@Component
@Transactional
public class JpaUserDetailsManager implements UserDetailsManager {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private OtpRepository otpRepository;

    private Logger logger = Logger.getLogger(JpaUserDetailsManager.class.getName());

    public JpaUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRepository = otpRepository;
    }

    public void authenticateUser(UserDetails userDetails) {

        logger.info("Authentication on a user.");

        Optional<User> found = userRepository.findByUsername(userDetails.getUsername());

        if (found.isPresent()) {
            User user = found.get();

            if (passwordEncoder.matches(userDetails.getPassword(), user.getPassword())) {
                issueOtp(userDetails);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void issueOtp(UserDetails userDetails) {

        logger.info("Issuance of an OTP for a user.");

        String otpCode = OneTimePasswordUtil.generateCode();

        Optional<Otp> foundOtp = otpRepository.findOtpByUsername(userDetails.getUsername());
        Optional<User> foundUser = userRepository.findByUsername(userDetails.getUsername());

        if (foundOtp.isPresent()) {
            Otp otp = foundOtp.get();
            otp.setOtpCode(otpCode);
        } else {

            Otp otp = new Otp(userDetails.getUsername(), otpCode);

            if (foundUser.isPresent()) {
                User user = foundUser.get();
                otp.setUser(user);
            } else {
                throw new UsernameNotFoundException("User not found");
            }
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp otpToBeChecked) {

        logger.info("Retrieval and validation on OTP.");
        
        Optional<Otp> found = otpRepository.findOtpByUsername(otpToBeChecked.getUsername());

        if (found.isPresent()) {
            Otp otp = found.get();

            if (otpToBeChecked.getOtpCode().equals(otp.getOtpCode())) {
                logger.info("Valid credentials.");
                return true;
            } else {
                logger.info("Invalid credentials.");
                return false;
            }
        } else {
            throw new UsernameNotFoundException("Otp not found by username.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        logger.info("Loading a user by the username.");
        Optional<User> found = userRepository.findByUsername(s);

        if (found.isPresent()) {
            return new SecurityUser(found.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
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

        Optional<User> found = userRepository.findByUsername(userDetails.getUsername());

        if (found.isPresent()) {
            User user = found.get();
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

            logger.info("Updating a user with the username of '" + user.getUsername() + "'.");
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void deleteUser(String s) {

        Optional<User> found = userRepository.findByUsername(s);

        if (found.isPresent()) {
            User user = found.get();

            logger.info("Deleting a user with the username of '" + user.getUsername() + "'.");
            userRepository.delete(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void changePassword(String s, String s1) {

        Optional<User> found = userRepository.findByUsername(s);

        if (found.isPresent()) {
            User user = found.get();
            user.setPassword(passwordEncoder.encode(s1));

            logger.info("Updating a user with the username of '" + user.getUsername() + "'.");
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public boolean userExists(String username) {

        Optional<User> found = userRepository.findByUsername(username);

        return found.isPresent();
    }
}
