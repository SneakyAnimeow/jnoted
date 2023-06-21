package club.anims.jnoted.controllers;

import club.anims.jnoted.data.dtos.UserDto;
import club.anims.jnoted.data.models.User;
import club.anims.jnoted.data.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public UserDto test() {
        var user = new User();
        user.setName("test");
        user.setHash("test-hash");
        user.setJoinDate(LocalDateTime.now());

        userRepository.save(user);

        return new UserDto(user);
    }
}
