package springbootApplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
import springbootApplication.service.RecipeService;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = Recipe.builder()
                .title("테스트 레시피")
                .difficulty(Difficulty.MEDIUM)
                .preparationTime(30)
                .build();
    }

    @Test
    void getAllRecipes_ShouldReturnRecipeList() {
        // Given
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        // When
        List<Recipe> result = recipeService.getAllRecipes();

        // Then
        assertEquals(1, result.size());
        assertEquals("테스트 레시피", result.get(0).getTitle());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById_ShouldReturnRecipe() {
        // Given
        Long recipeId = 1L;
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        // When
        Optional<Recipe> result = recipeService.getRecipeById(recipeId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("테스트 레시피", result.get().getTitle());
        verify(recipeRepository, times(1)).findById(recipeId);
    }

    @Test
    void saveRecipe_ShouldSaveAndReturnRecipe() {
        // Given
        RecipeRequestDto dto = new RecipeRequestDto("새로운 레시피", Difficulty.EASY, 15);
        Recipe savedRecipe = Recipe.builder()
                .title(dto.getTitle())
                .difficulty(dto.getDifficulty())
                .preparationTime(dto.getPreparationTime())
                .build();

        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        // When
        Recipe result = recipeService.saveRecipe(dto);

        // Then
        assertNotNull(result);
        assertEquals("새로운 레시피", result.getTitle());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void updateRecipe_ShouldUpdateAndReturnUpdatedRecipe() {
        // Given
        Long recipeId = 1L;
        Recipe updatedRecipe = Recipe.builder()
                .title("업데이트된 레시피")
                .difficulty(Difficulty.HARD)
                .preparationTime(45)
                .build();

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(updatedRecipe);

        // When
        Recipe result = recipeService.updateRecipe(recipeId, updatedRecipe);

        // Then
        assertNotNull(result);
        assertEquals("업데이트된 레시피", result.getTitle());
        verify(recipeRepository, times(1)).findById(recipeId);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteRecipe_ShouldDeleteRecipeIfExists() {
        // Given
        Long recipeId = 1L;
        when(recipeRepository.existsById(recipeId)).thenReturn(true);
        doNothing().when(recipeRepository).deleteById(recipeId);

        // When
        recipeService.deleteRecipe(recipeId);

        // Then
        verify(recipeRepository, times(1)).existsById(recipeId);
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    void findRecipesByKeyword_ShouldReturnMatchingRecipes() {
        // Given
        String keyword = "테스트";
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByTitleContaining(keyword)).thenReturn(recipes);

        // When
        List<Recipe> result = recipeService.findRecipesByKeyword(keyword);

        // Then
        assertEquals(1, result.size());
        assertEquals("테스트 레시피", result.get(0).getTitle());
        verify(recipeRepository, times(1)).findByTitleContaining(keyword);
    }

    @Test
    void findByDifficulty_ShouldReturnRecipesWithDifficulty() {
        // Given
        Difficulty difficulty = Difficulty.MEDIUM;
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByDifficulty(difficulty)).thenReturn(recipes);

        // When
        List<Recipe> result = recipeService.findByDifficulty(difficulty);

        // Then
        assertEquals(1, result.size());
        assertEquals(Difficulty.MEDIUM, result.get(0).getDifficulty());
        verify(recipeRepository, times(1)).findByDifficulty(difficulty);
    }

    @Test
    void findRecipesByPreparationTime_ShouldReturnRecipesWithGivenTime() {
        // Given
        int preparationTime = 30;
        List<Recipe> recipes = Arrays.asList(recipe);
        when(recipeRepository.findByPreparationTime(preparationTime)).thenReturn(recipes);

        // When
        List<Recipe> result = recipeService.findRecipesByPreparationTime(preparationTime);

        // Then
        assertEquals(1, result.size());
        assertEquals(30, result.get(0).getPreparationTime());
        verify(recipeRepository, times(1)).findByPreparationTime(preparationTime);
    }
}
