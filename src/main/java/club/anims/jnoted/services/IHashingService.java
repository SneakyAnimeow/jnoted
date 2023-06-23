package club.anims.jnoted.services;

public interface IHashingService {
    /**
     * @param password password to hash
     * @return hashed password
     */
    String hash(String password);

    /**
     * @param password password to verify
     * @param hash     hash to verify
     * @return true if password matches hash, false otherwise
     */
    boolean verify(String password, String hash);
}
