package practice.authorization.repository;

import org.springframework.data.repository.CrudRepository;
import practice.authorization.entity.Otp;

import java.util.Optional;

public interface OtpRepository extends CrudRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}
