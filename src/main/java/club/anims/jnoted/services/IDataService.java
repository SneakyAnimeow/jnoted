package club.anims.jnoted.services;

import club.anims.jnoted.controllers.UserController;
import club.anims.jnoted.data.dtos.CategoryDto;
import club.anims.jnoted.data.dtos.NoteDto;
import club.anims.jnoted.data.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface IDataService {
    List<CategoryDto> getCategoriesByToken(String token);

    Optional<CategoryDto> getCategoryByTokenAndId(String token, Long id);

    CategoryDto addCategoryByToken(String token, CategoryDto entity);

    CategoryDto updateCategoryByTokenAndId(String token, long id, CategoryDto entity);

    void deleteCategoryByTokenAndId(String token, long id);

    List<NoteDto> getNotesByTokenAndCategoryId(String token, long categoryId);

    Optional<NoteDto> getNoteByTokenAndId(String token, long id);

    NoteDto addNoteByTokenAndCategoryId(String token, long categoryId, NoteDto entity);

    NoteDto updateNoteByTokenAndId(String token, long id, NoteDto entity);

    void deleteNoteByTokenAndId(String token, long id);
    
    Optional<UserDto> getUserByToken(String token);

    UserDto updateUserByToken(UserController.UpdateUserBody body);
}
