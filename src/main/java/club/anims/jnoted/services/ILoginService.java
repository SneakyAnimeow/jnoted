package club.anims.jnoted.services;

public interface ILoginService {
    String login(String name, String password);

    void logout(String token);

    String register(String name, String password, String email);

    boolean tokenStillValid(String token);
}
