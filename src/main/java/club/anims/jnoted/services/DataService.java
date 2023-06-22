package club.anims.jnoted.services;

import club.anims.jnoted.controllers.UserController;
import club.anims.jnoted.data.dtos.CategoryDto;
import club.anims.jnoted.data.dtos.NoteDto;
import club.anims.jnoted.data.dtos.UserDto;
import club.anims.jnoted.data.models.Category;
import club.anims.jnoted.data.models.Note;
import club.anims.jnoted.data.models.User;
import club.anims.jnoted.data.repositories.CategoryRepository;
import club.anims.jnoted.data.repositories.NoteRepository;
import club.anims.jnoted.data.repositories.TokenRepository;
import club.anims.jnoted.data.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DataService implements IDataService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final IHashingService hashingService;

    public DataService(TokenRepository tokenRepository, UserRepository userRepository, NoteRepository noteRepository, CategoryRepository categoryRepository, IHashingService hashingService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
        this.hashingService = hashingService;
    }

    @Override
    public List<CategoryDto> getCategoriesByToken(String token) {
        var user = getUserEntityByToken(token);
        var categories = categoryRepository.findByUser_Id(user.getId());

        return categories.stream().map(CategoryDto::new).toList();
    }

    @Override
    public Optional<CategoryDto> getCategoryByTokenAndId(String token, Long id) {
        var user = getUserEntityByToken(token);
        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        return category.map(CategoryDto::new);
    }

    @Override
    public CategoryDto addCategoryByToken(String token, CategoryDto entity) {
        var user = getUserEntityByToken(token);

        var category = new Category(entity);

        category.setUser(user);

        category = categoryRepository.save(category);

        return new CategoryDto(category);
    }

    @Override
    public CategoryDto updateCategoryByTokenAndId(String token, long id, CategoryDto entity) {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        category.get().setName(entity.getName());

        var savedCategory = categoryRepository.save(category.get());

        return new CategoryDto(savedCategory);
    }

    @Override
    public void deleteCategoryByTokenAndId(String token, long id) {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.delete(category.get());
    }

    @Override
    public List<NoteDto> getNotesByTokenAndCategoryId(String token, long categoryId) {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(categoryId);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        var notes = noteRepository.findByCategory_Id(categoryId);

        return notes.stream().map(NoteDto::new).toList();
    }

    @Override
    public Optional<NoteDto> getNoteByTokenAndId(String token, long id) {
        var user = getUserEntityByToken(token);

        var note = noteRepository.findById(id);

        if (note.isEmpty() || !Objects.equals(note.get().getCategory().getUser().getId(), user.getId())) {
            throw new RuntimeException("Note not found");
        }

        return note.map(NoteDto::new);
    }

    @Override
    public NoteDto addNoteByTokenAndCategoryId(String token, long categoryId, NoteDto entity) {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(categoryId);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        var note = new Note();

        note.setCategory(category.get());
        note.setName(entity.getName());
        note.setContent(entity.getContent());

        note = noteRepository.save(note);

        return new NoteDto(note);
    }

    @Override
    public NoteDto updateNoteByTokenAndId(String token, long id, NoteDto entity) {
        var user = getUserEntityByToken(token);

        var note = noteRepository.findById(id);

        if (note.isEmpty() || !Objects.equals(note.get().getCategory().getUser().getId(), user.getId())) {
            throw new RuntimeException("Note not found");
        }

        note.get().setName(entity.getName());
        note.get().setContent(entity.getContent());

        var savedNote = noteRepository.save(note.get());

        return new NoteDto(savedNote);
    }

    @Override
    public void deleteNoteByTokenAndId(String token, long id) {
        var user = getUserEntityByToken(token);

        var note = noteRepository.findById(id);

        if (note.isEmpty() || !Objects.equals(note.get().getCategory().getUser().getId(), user.getId())) {
            throw new RuntimeException("Note not found");
        }

        noteRepository.delete(note.get());
    }

    @Override
    public Optional<UserDto> getUserByToken(String token) {
        var user = getUserEntityByToken(token);

        return Optional.of(new UserDto(user));
    }

    @Override
    public UserDto updateUserByToken(UserController.UpdateUserBody body) {
        var user = getUserEntityByToken(body.token());

        user.setName(body.username());
        user.setEmail(body.email());

        if(body.password().isPresent()) {
            user.setHash(hashingService.hash(body.password().get()));
        }

        var savedUser = userRepository.save(user);

        return new UserDto(savedUser);
    }

    private User getUserEntityByToken(String token) throws RuntimeException {
        var tokenEntity = tokenRepository.findByToken(token);
        if (tokenEntity.isEmpty()) {
            throw new RuntimeException("Token not found");
        }

        if(tokenEntity.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        return tokenEntity.get().getUser();
    }
}
