package springbootApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class AddUserRequest {

<<<<<<< HEAD
=======
	  
	@NotNull(message = "Id cannot be null")
    private Long userId;

>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
<<<<<<< HEAD
    
=======

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
    private String name;
    
    public AddUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
