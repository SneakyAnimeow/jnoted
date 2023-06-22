package club.anims.jnoted.services;

import club.anims.jnoted.data.models.Token;
import club.anims.jnoted.data.models.User;
import club.anims.jnoted.data.repositories.TokenRepository;
import club.anims.jnoted.data.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class LoginService implements ILoginService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final IHashingService hashingService;

    public LoginService(TokenRepository tokenRepository, UserRepository userRepository, IHashingService hashingService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public String login(String name, String password) {
        var user = userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User not found"));

        if(!hashingService.verify(password, user.getHash())) {
            throw new RuntimeException("Wrong password");
        }

        var token = new Token(user, UUID.randomUUID().toString(), LocalDateTime.now().plusHours(12));
        token = tokenRepository.save(token);

        log.info("User logged in: %s".formatted(name));

        return token.getToken();
    }

    @Override
    public void logout(String token) {
        if(!tokenRepository.existsByToken(token)) {
            throw new RuntimeException("Token not found");
        }

        tokenRepository.deleteByToken(token);

        log.info("User logged out: %s".formatted(token));
    }

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
