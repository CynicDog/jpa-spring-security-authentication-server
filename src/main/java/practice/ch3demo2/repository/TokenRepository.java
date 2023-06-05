package practice.ch3demo2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import practice.ch3demo2.entity.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findTokenBySessionId(String sessionId);
}
