package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Recipe;
import springbootApplication.domain.Difficulty;
import springbootApplication.dto.RecipeRequestDto;
import springbootApplication.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebPushService webPushService;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = Recipe.builder()
                .RecipeId(1L)
                .title("Test Recipe")
                .difficulty(Difficulty.MEDIUM)
                .preparationTime(30)
                .build();
    }

    @Test
    void testGetAllRecipes() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<Recipe> result = recipeService.getAllRecipes();

        assertEquals(1, result.size());
        assertEquals("Test Recipe", result.get(0).getTitle());
    }

    @Test
    void testGetRecipeById_Found() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> result = recipeService.getRecipeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Recipe", result.get().getTitle());
    }

    @Test
    void testGetRecipeById_NotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.getRecipeById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void testSaveRecipe() {
        RecipeRequestDto dto = new RecipeRequestDto("New Recipe", Difficulty.HARD, 45);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe savedRecipe = recipeService.saveRecipe(dto);

        assertNotNull(savedRecipe);
        assertEquals("Test Recipe", savedRecipe.getTitle());
        assertEquals(Difficulty.MEDIUM, savedRecipe.getDifficulty());
        assertEquals(30, savedRecipe.getPreparationTime());
    }

    @Test
    void testSaveRecipeWithInvalidDifficulty() {
        RecipeRequestDto dto = new RecipeRequestDto("New Recipe", null, new ArrayList<>(), 45, "Instructions");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.saveRecipe(dto));
        
        assertEquals("Difficulty value cannot be null", exception.getMessage());

    }


    @Test
    void testUpdateRecipe_Success() {
        Recipe updatedRecipe = Recipe.builder()
                .RecipeId(1L)
                .title("Updated Recipe")
                .difficulty(Difficulty.EASY)
                .preparationTime(20)
                .instructions("New instructions")
                .build();

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        Recipe result = recipeService.updateRecipe(1L, updatedRecipe);

        assertNotNull(result);
        assertEquals("Updated Recipe", result.getTitle());
        assertEquals(Difficulty.EASY, result.getDifficulty());
        assertEquals(20, result.getPreparationTime());
    }

    @Test
    void testUpdateRecipe_NotFound() {
        Recipe updatedRecipe = Recipe.builder()
                .RecipeId(1L)
                .title("Updated Recipe")
                .build();

        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.updateRecipe(1L, updatedRecipe));

        assertEquals("Recipe not found", exception.getMessage());
    }

    @Test
    void testDeleteRecipe_Success() {
        when(recipeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(1L);

        assertDoesNotThrow(() -> recipeService.deleteRecipe(1L));
        verify(recipeRepository).deleteById(1L);
    }

    @Test
    void testDeleteRecipe_NotFound() {
        when(recipeRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recipeService.deleteRecipe(1L));

        assertEquals("Recipe not found", exception.getMessage());
    }

    @Test
    void testFindRecipesByKeyword() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByTitleContaining("Test")).thenReturn(recipes);

        List<Recipe> result = recipeService.findRecipesByKeyword("Test");

        assertEquals(1, result.size());
        assertEquals("Test Recipe", result.get(0).getTitle());
    }

    @Test
    void testFindByDifficulty() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByDifficulty(Difficulty.MEDIUM)).thenReturn(recipes);

        List<Recipe> result = recipeService.findByDifficulty(Difficulty.MEDIUM);

        assertEquals(1, result.size());
        assertEquals(Difficulty.MEDIUM, result.get(0).getDifficulty());
    }

    @Test
    void testFindRecipesByPreparationTime() {
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByPreparationTime(30)).thenReturn(recipes);

        List<Recipe> result = recipeService.findRecipesByPreparationTime(30);

        assertEquals(1, result.size());
        assertEquals(30, result.get(0).getPreparationTime());
    }
}
