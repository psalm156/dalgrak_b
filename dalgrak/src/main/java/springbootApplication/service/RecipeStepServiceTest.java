package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.RecipeStep;
import springbootApplication.repository.RecipeStepRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeStepServiceTest {

    @Mock
    private RecipeStepRepository recipeStepRepository;

    @InjectMocks
    private RecipeStepService recipeStepService;

    private RecipeStep recipeStep;

    @BeforeEach
    void setUp() {
        recipeStep = new RecipeStep();
        recipeStep.setId(1L);
        recipeStep.setInstruction("Chop the vegetables");
        recipeStep.setEstimatedTime(10);
    }

    @Test
    void testGetAllRecipeSteps() {
        when(recipeStepRepository.findAll()).thenReturn(List.of(recipeStep));

        List<RecipeStep> steps = recipeStepService.getAllRecipeSteps();

        assertFalse(steps.isEmpty());
        assertEquals(1, steps.size());
        assertEquals("Chop the vegetables", steps.get(0).getInstruction());
    }

    @Test
    void testGetRecipeStepById_Found() {
        when(recipeStepRepository.findById(1L)).thenReturn(Optional.of(recipeStep));

        Optional<RecipeStep> foundStep = recipeStepService.getRecipeStepById(1L);

        assertTrue(foundStep.isPresent());
    }

    @Test
    void testGetRecipeStepById_NotFound() {
        when(recipeStepRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<RecipeStep> foundStep = recipeStepService.getRecipeStepById(2L);

        assertFalse(foundStep.isPresent());
    }

    @Test
    void testCreateRecipeStep() {
        when(recipeStepRepository.save(any(RecipeStep.class))).thenReturn(recipeStep);

        RecipeStep savedStep = recipeStepService.createRecipeStep(recipeStep);

        assertNotNull(savedStep);
        verify(recipeStepRepository, times(1)).save(recipeStep);
    }

    @Test
    void testUpdateRecipeStep_Success() {
        RecipeStep updatedStep = new RecipeStep();
        updatedStep.setInstruction("Boil water");
        updatedStep.setEstimatedTime(5);

        when(recipeStepRepository.findById(1L)).thenReturn(Optional.of(recipeStep));
        when(recipeStepRepository.save(any(RecipeStep.class))).thenReturn(updatedStep);

        RecipeStep result = recipeStepService.updateRecipeStep(1L, updatedStep);

        assertEquals("Boil water", result.getInstruction());
    }

    @Test
    void testUpdateRecipeStep_NotFound() {
        when(recipeStepRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> recipeStepService.updateRecipeStep(1L, recipeStep));

        assertEquals("Recipe step not found", exception.getMessage());
    }

    @Test
    void testDeleteRecipeStep() {
        doNothing().when(recipeStepRepository).deleteById(1L);

        assertDoesNotThrow(() -> recipeStepService.deleteRecipeStep(1L));

        verify(recipeStepRepository, times(1)).deleteById(1L);
    }
}
