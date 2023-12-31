package club.anims.jnoted.data.repositories;

import club.anims.jnoted.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * @return User entity by name
     */
    @Query("select u from User u where u.name = ?1")
    Optional<User> findByName(String name);

    /**
     * @return true if user exists by name or email
     */
    @Query("select (count(u) > 0) from User u where u.name = ?1 or u.email = ?2")
    boolean existsByNameOrEmail(String name, String email);
}