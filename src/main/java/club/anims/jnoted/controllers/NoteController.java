package club.anims.jnoted.controllers;

import club.anims.jnoted.data.dtos.NoteDto;
import club.anims.jnoted.services.IDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Note")
public class NoteController {
    private final IDataService dataService;

    public NoteController(IDataService dataService) {
        this.dataService = dataService;
    }

    public record GetNotesBody(String token, long categoryId){}
    @PostMapping("/GetNotes")
    public List<NoteDto> getNotes(@RequestBody GetNotesBody body) {
        return dataService.getNotesByTokenAndCategoryId(body.token, body.categoryId);
    }

    public record GetNoteBody(String token, long id){}
    @PostMapping("/GetNote")
    public Optional<NoteDto> getNote(@RequestBody GetNoteBody body) {
        return dataService.getNoteByTokenAndId(body.token, body.id);
    }

    public record CreateNoteBody(String token, long categoryId, String title, String content){}
    @PostMapping("/CreateNote")
    public NoteDto createNote(@RequestBody CreateNoteBody body) {
        return dataService.addNoteByTokenAndCategoryId(body.token, body.categoryId, new NoteDto(0L, body.title, body.content));
    }

    public record UpdateNoteBody(String token, long id, String title, String content){}
    @PostMapping("/UpdateNote")
    public NoteDto updateNote(@RequestBody UpdateNoteBody body) {
        return dataService.updateNoteByTokenAndId(body.token, body.id, new NoteDto(0L, body.title, body.content));
    }

    public record DeleteNoteBody(String token, long id){}
    @PostMapping("/DeleteNote")
    public void deleteNote(@RequestBody DeleteNoteBody body) {
        dataService.deleteNoteByTokenAndId(body.token, body.id);
    }
}
