package club.anims.jnoted.controllers;

import club.anims.jnoted.services.ILoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    public record LoginBody(String username, String password) { }
    @PostMapping("/Login")
    public String login(@RequestBody LoginBody body) {
        return loginService.login(body.username(), body.password());
    }

    public record LogoutBody(String token) { }
    @PostMapping("/Logout")
    public void logout(@RequestBody LogoutBody body) {
        loginService.logout(body.token());
    }

    public record RegisterBody(String username, String password, String email) { }
    @PostMapping("/Register")
    public String register(@RequestBody RegisterBody body) {
        return loginService.register(body.username(), body.password(), body.email());
    }

    public record TokenBody(String token) { }
    @PostMapping("/TokenStillValid")
    public boolean tokenStillValid(@RequestBody TokenBody body) {
        return loginService.tokenStillValid(body.token());
    }
}
