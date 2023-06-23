package club.anims.jnoted.schedulers;

import club.anims.jnoted.data.repositories.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@EnableAsync
@EnableScheduling
public class DatabaseScheduler {
    private static final int TOKENS_CLEANUP_INTERVAL = 1000 * 60 * 60; // 1 hour

    private final TokenRepository tokenRepository;

    /**
     * @param tokenRepository Token repository
     */
    public DatabaseScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * This method is used to clean up tokens.
     */
    @Schedules({
            @Scheduled(fixedDelay = TOKENS_CLEANUP_INTERVAL)
    })
    public void cleanupTokens() {
        tokenRepository.deleteByExpirationDateLessThan(LocalDateTime.now());
        log.info("Tokens cleanup done");
    }
}
