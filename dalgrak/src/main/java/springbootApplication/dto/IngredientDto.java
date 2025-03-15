package springbootApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class IngredientDto {
	@NotNull(message = "Ingredient ID cannot be null")
	private Long IngredientId;
	
	@NotBlank(message = "Ingredient name cannot be blank")
	@Size(max = 50, message = "Ingredient name must be at most 50 characters long")
	private String name;
	
	 @NotBlank(message = "Quantity cannot be blank")
	    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?\\s?(g|kg|ml|l|cup|tbsp|tsp|pcs)?$", 
	             message = "Quantity must be a valid number followed by an optional unit (e.g., '200g', '2 cups')")
	private String Quantity;

}
