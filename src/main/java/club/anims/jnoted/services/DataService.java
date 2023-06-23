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

    /**
     * @param tokenRepository Token repository
     * @param userRepository User repository
     * @param noteRepository Note repository
     * @param categoryRepository Category repository
     * @param hashingService Hashing service
     */
    public DataService(TokenRepository tokenRepository, UserRepository userRepository, NoteRepository noteRepository, CategoryRepository categoryRepository, IHashingService hashingService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
        this.hashingService = hashingService;
    }

    /**
     * @return List of categories for user by token
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public List<CategoryDto> getCategoriesByToken(String token) throws RuntimeException {
        var user = getUserEntityByToken(token);
        var categories = categoryRepository.findByUser_Id(user.getId());

        return categories.stream().map(CategoryDto::new).toList();
    }

    /**
     * @return Category for user by id
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public Optional<CategoryDto> getCategoryByTokenAndId(String token, Long id) throws RuntimeException {
        var user = getUserEntityByToken(token);
        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        return category.map(CategoryDto::new);
    }

    /**
     * @return Added category
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public CategoryDto addCategoryByToken(String token, CategoryDto entity) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var category = new Category(entity);

        category.setUser(user);

        category = categoryRepository.save(category);

        return new CategoryDto(category);
    }

    /**
     * @return Updated category
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public CategoryDto updateCategoryByTokenAndId(String token, long id, CategoryDto entity) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        category.get().setName(entity.getName());

        var savedCategory = categoryRepository.save(category.get());

        return new CategoryDto(savedCategory);
    }

    /**
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public void deleteCategoryByTokenAndId(String token, long id) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(id);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.delete(category.get());
    }

    /**
     * @return List of notes for user by token and category id
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public List<NoteDto> getNotesByTokenAndCategoryId(String token, long categoryId) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var category = categoryRepository.findById(categoryId);

        if (category.isEmpty() || !Objects.equals(category.get().getUser().getId(), user.getId())) {
            throw new RuntimeException("Category not found");
        }

        var notes = noteRepository.findByCategory_Id(categoryId);

        return notes.stream().map(NoteDto::new).toList();
    }

    /**
     * @return Note for user by token and id
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public Optional<NoteDto> getNoteByTokenAndId(String token, long id) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var note = noteRepository.findById(id);

        if (note.isEmpty() || !Objects.equals(note.get().getCategory().getUser().getId(), user.getId())) {
            throw new RuntimeException("Note not found");
        }

        return note.map(NoteDto::new);
    }

    /**
     * @return Added note
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public NoteDto addNoteByTokenAndCategoryId(String token, long categoryId, NoteDto entity) throws RuntimeException {
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

    /**
     * @return Updated note
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public NoteDto updateNoteByTokenAndId(String token, long id, NoteDto entity) throws RuntimeException {
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

    /**
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public void deleteNoteByTokenAndId(String token, long id) throws RuntimeException {
        var user = getUserEntityByToken(token);

        var note = noteRepository.findById(id);

        if (note.isEmpty() || !Objects.equals(note.get().getCategory().getUser().getId(), user.getId())) {
            throw new RuntimeException("Note not found");
        }

        noteRepository.delete(note.get());
    }

    /**
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public Optional<UserDto> getUserByToken(String token) throws RuntimeException {
        var user = getUserEntityByToken(token);

        return Optional.of(new UserDto(user));
    }

    /**
     * @throws RuntimeException If user not found or password is wrong
     */
    @Override
    public UserDto updateUserByToken(UserController.UpdateUserBody body) throws RuntimeException {
        var user = getUserEntityByToken(body.token());

        user.setName(body.username());
        user.setEmail(body.email());

        if(body.password().isPresent()) {
            user.setHash(hashingService.hash(body.password().get()));
        }

        var savedUser = userRepository.save(user);

        return new UserDto(savedUser);
    }

    /**
     * @param token Token
     * @throws RuntimeException If user not found or password is wrong
     */
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
