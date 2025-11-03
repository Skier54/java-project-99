package hexlet.code.dto.dtoUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {

    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(min = 2, max = 30)
    private String firstName;

    @Size(min = 2, max = 30)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 30)
    private String password;
}
