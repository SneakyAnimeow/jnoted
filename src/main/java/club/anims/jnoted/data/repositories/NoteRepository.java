package club.anims.jnoted.data.repositories;

import club.anims.jnoted.data.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("select n from Note n where n.category.id = ?1")
    List<Note> findByCategory_Id(Long id);
}