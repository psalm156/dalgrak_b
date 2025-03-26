package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Ingredient;
import springbootApplication.repository.IngredientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient();
        ingredient.setIngredientId(1L);
        ingredient.setName("Tomato");
    }

    @Test
    void testCreateIngredient() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);

        assertNotNull(createdIngredient);
        assertEquals("Tomato", createdIngredient.getName());

        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void testGetAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));

        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        assertEquals("Tomato", ingredients.get(0).getName());
    }

    @Test
    void testGetIngredientById() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        Optional<Ingredient> foundIngredient = ingredientService.getIngredientById(1L);

        assertTrue(foundIngredient.isPresent());
        assertEquals("Tomato", foundIngredient.get().getName());
    }

    @Test
    void testUpdateIngredient() {
        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setName("Lettuce");

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(updatedIngredient);

        Ingredient result = ingredientService.updateIngredient(1L, updatedIngredient);

        assertNotNull(result);
        assertEquals("Lettuce", result.getName());

        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void testDeleteIngredient() {
        doNothing().when(ingredientRepository).deleteById(1L);

        ingredientService.deleteIngredient(1L);
        verify(ingredientRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateIngredientNotFound() {
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> ingredientService.updateIngredient(1L, ingredient));
        assertEquals("Ingredient not found", thrown.getMessage());
    }
}
