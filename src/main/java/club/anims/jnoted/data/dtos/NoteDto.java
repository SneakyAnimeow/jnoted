package club.anims.jnoted.data.dtos;

import club.anims.jnoted.data.models.Note;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link club.anims.jnoted.data.models.Note}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto implements Serializable {
    private Long id;
    private String name;
    private String content;

    public NoteDto(Note note) {
        this.id = note.getId();
        this.name = note.getName();
        this.content = note.getContent();
    }
}