package club.anims.jnoted.services;

import club.anims.jnoted.data.models.Token;
import club.anims.jnoted.data.models.User;
import club.anims.jnoted.data.repositories.TokenRepository;
import club.anims.jnoted.data.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class LoginService implements ILoginService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final IHashingService hashingService;

    /**
     * @param tokenRepository Token repository
     * @param userRepository User repository
     * @param hashingService Hashing service
     */
    public LoginService(TokenRepository tokenRepository, UserRepository userRepository, IHashingService hashingService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    /**
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public String login(String name, String password) throws RuntimeException {
        var user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found"));

        if(!hashingService.verify(password, user.getHash())) {
            throw new RuntimeException("Wrong password");
        }

        var token = new Token(user, UUID.randomUUID().toString(), LocalDateTime.now().plusHours(12));
        token = tokenRepository.save(token);

        log.info("User logged in: %s".formatted(name));

        return token.getToken();
    }

    /**
     * @throws RuntimeException If token not found
     */
    @Override
    public void logout(String token) throws RuntimeException {
        if(!tokenRepository.existsByToken(token)) {
            throw new RuntimeException("Token not found");
        }

        tokenRepository.deleteByToken(token);

        log.info("User logged out: %s".formatted(token));
    }

    /**
     * @throws RuntimeException If user already exists
     */
    @Override
    public String register(String name, String password, String email) throws RuntimeException {
        if(userRepository.existsByNameOrEmail(name, email)) {
            throw new RuntimeException("User already exists");
        }

        var user = new User(name, hashingService.hash(password), email);
        userRepository.save(user);

        log.info("User registered: %s with mail %s".formatted(name, email));

        return login(name, password);
    }

    @Override
    public boolean tokenStillValid(String token) {
        if(!tokenRepository.existsByToken(token)) {
            return false;
        }

        var tokenEntity = tokenRepository.findByToken(token);

        return tokenEntity.map(value -> value.getExpirationDate().isAfter(LocalDateTime.now())).orElse(false);
    }
}
