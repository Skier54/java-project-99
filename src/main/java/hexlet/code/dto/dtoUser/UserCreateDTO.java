package hexlet.code.dto.dtoUser;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class UserCreateDTO {

    @NotBlank
    @Email
    @Size(min = 5, max = 100)
    private String email;

    //@Size(min = 2, max = 30)
    private JsonNullable<String> firstName;

    //@Size(min = 2, max = 30)
    private JsonNullable<String> lastName;

    @NotBlank
    @Size(min = 3)
    private String password;
}
