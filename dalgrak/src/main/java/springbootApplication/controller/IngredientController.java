package springbootApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springbootApplication.domain.Ingredient;
import springbootApplication.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // 모든 재료 조회
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    // 특정 재료 조회
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id).orElseThrow(() -> new RuntimeException("Ingredient not found")));
    }

    // 재료 추가
    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.createIngredient(ingredient));
    }

    // 재료 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, updatedIngredient));
    }

    // 재료 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
