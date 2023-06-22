package club.anims.jnoted.data.repositories;

import club.anims.jnoted.data.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.user.id = ?1")
    List<Category> findByUser_Id(Long id);
}