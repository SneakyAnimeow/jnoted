package club.anims.jnoted.controllers;

import club.anims.jnoted.data.dtos.UserDto;
import club.anims.jnoted.services.IDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/User")
public class UserController {
    private final IDataService dataService;

    public UserController(IDataService dataService) {
        this.dataService = dataService;
    }

    public record GetInfoBody(String token){}
    @PostMapping("/GetInfo")
    public Optional<UserDto> getInfo(@RequestBody GetInfoBody body) {
        return dataService.getUserByToken(body.token);
    }

    public record UpdateUserBody(String token, String username, String email, Optional<String> password){}
    @PostMapping("/UpdateUser")
    public UserDto updateUser(@RequestBody UpdateUserBody body) {
        return dataService.updateUserByToken(body);
    }
}
