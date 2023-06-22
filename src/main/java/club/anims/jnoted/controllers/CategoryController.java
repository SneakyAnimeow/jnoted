package club.anims.jnoted.controllers;

import club.anims.jnoted.data.dtos.CategoryDto;
import club.anims.jnoted.services.IDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Category")
public class CategoryController {
    private final IDataService dataService;

    public CategoryController(IDataService dataService) {
        this.dataService = dataService;
    }

    public record GetCategoriesBody(String token){}
    @PostMapping("/GetCategories")
    public List<CategoryDto> getCategories(@RequestBody GetCategoriesBody body) {
        return dataService.getCategoriesByToken(body.token);
    }

    public record GetCategoryBody(String token, long id){}
    @PostMapping("/GetCategory")
    public Optional<CategoryDto> getCategory(@RequestBody GetCategoryBody body) {
        return dataService.getCategoryByTokenAndId(body.token, body.id);
    }

    public record CreateCategoryBody(String token, String name){}
    @PostMapping("/CreateCategory")
    public CategoryDto createCategory(@RequestBody CreateCategoryBody body) {
        return dataService.addCategoryByToken(body.token, new CategoryDto(0L, body.name));
    }

    public record UpdateCategoryBody(String token, long id, String name){}
    @PostMapping("/UpdateCategory")
    public CategoryDto updateCategory(@RequestBody UpdateCategoryBody body) {
        return dataService.updateCategoryByTokenAndId(body.token, body.id, new CategoryDto(0L, body.name));
    }

    public record DeleteCategoryBody(String token, long id){}
    @PostMapping("/DeleteCategory")
    public void deleteCategory(@RequestBody DeleteCategoryBody body) {
         dataService.deleteCategoryByTokenAndId(body.token, body.id);
    }
}
