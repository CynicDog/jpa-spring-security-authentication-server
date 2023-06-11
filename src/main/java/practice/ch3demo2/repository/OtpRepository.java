package practice.ch3demo2.repository;

import org.springframework.data.repository.CrudRepository;
import practice.ch3demo2.entity.Otp;

import java.util.Optional;

public interface OtpRepository extends CrudRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}
