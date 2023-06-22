package club.anims.jnoted.services;

public interface IHashingService {
    String hash(String password);

    boolean verify(String password, String hash);
}
