package club.anims.jnoted.services;

public interface ILoginService {
    /**
     * @param name     user name
     * @param password user password
     * @return token if login successful, null otherwise
     */
    String login(String name, String password);

    /**
     * @param token token to logout
     */
    void logout(String token);

    /**
     * @param name     user name
     * @param password user password
     * @param email    user email
     * @return token if register successful, null otherwise
     */
    String register(String name, String password, String email);

    /**
     * @param token token to check
     * @return true if token is valid, false otherwise
     */
    boolean tokenStillValid(String token);
}
