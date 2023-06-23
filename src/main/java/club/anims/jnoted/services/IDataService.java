package club.anims.jnoted.services;

import club.anims.jnoted.controllers.UserController;
import club.anims.jnoted.data.dtos.CategoryDto;
import club.anims.jnoted.data.dtos.NoteDto;
import club.anims.jnoted.data.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface IDataService {
    /**
     * @param token token to verify
     * @return List of categories if token is valid, null otherwise
     */
    List<CategoryDto> getCategoriesByToken(String token);

    /**
     * @param token token to verify
     * @param id    id of category to get
     * @return category if token is valid, null otherwise
     */
    Optional<CategoryDto> getCategoryByTokenAndId(String token, Long id);

    /**
     * @param token  token to verify
     * @param entity category to add
     * @return added category if token is valid, null otherwise
     */
    CategoryDto addCategoryByToken(String token, CategoryDto entity);

    /**
     * @param token  token to verify
     * @param id     id of category to update
     * @param entity category to update
     * @return updated category if token is valid, null otherwise
     */
    CategoryDto updateCategoryByTokenAndId(String token, long id, CategoryDto entity);

    /**
     * @param token token to verify
     * @param id    id of category to delete
     */
    void deleteCategoryByTokenAndId(String token, long id);

    /**
     * @param token      token to verify
     * @param categoryId id of category to get notes from
     * @return List of notes if token is valid, null otherwise
     */
    List<NoteDto> getNotesByTokenAndCategoryId(String token, long categoryId);

    /**
     * @param token token to verify
     * @param id    id of note to get
     * @return note if token is valid, null otherwise
     */
    Optional<NoteDto> getNoteByTokenAndId(String token, long id);

    /**
     * @param token      token to verify
     * @param categoryId id of category to add note to
     * @param entity     note to add
     * @return added note if token is valid, null otherwise
     */
    NoteDto addNoteByTokenAndCategoryId(String token, long categoryId, NoteDto entity);

    /**
     * @param token  token to verify
     * @param id     id of note to update
     * @param entity note to update
     * @return updated note if token is valid, null otherwise
     */
    NoteDto updateNoteByTokenAndId(String token, long id, NoteDto entity);

    /**
     * @param token token to verify
     * @param id    id of note to delete
     */
    void deleteNoteByTokenAndId(String token, long id);

    /**
     * @param token token to verify
     * @return user if token is valid, null otherwise
     */
    Optional<UserDto> getUserByToken(String token);

    /**
     * @param body body to update user
     * @return updated user if token is valid, null otherwise
     */
    UserDto updateUserByToken(UserController.UpdateUserBody body);
}
