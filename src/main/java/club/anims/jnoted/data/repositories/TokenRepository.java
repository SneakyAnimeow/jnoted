package club.anims.jnoted.data.repositories;

import club.anims.jnoted.data.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    /**
     * @return True if token exists
     */
    @Query("select (count(t) > 0) from Token t where t.token = ?1")
    boolean existsByToken(String token);

    /**
     * @return Token entity by token
     */
    @Query("select t from Token t where t.token = ?1")
    Optional<Token> findByToken(String token);

    /**
     * @return int result of operation
     */
    @Transactional
    @Modifying
    @Query("delete from Token t where t.expirationDate < ?1")
    int deleteByExpirationDateLessThan(LocalDateTime expirationDate);

    /**
     * @return int result of operation
     */
    @Transactional
    @Modifying
    @Query("delete from Token t where t.token = ?1")
    int deleteByToken(String token);
}