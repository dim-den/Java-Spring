package movie.web.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class RegistrationRequestDTO {
    @NotBlank(message = "Please, enter the email!")
    @Email(message = "Wrong email address")
    private String email;

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 symbols")
    @Pattern(regexp = "^[A-z\\d*]{4,20}$", message = "Only letters and numbers allowed in username")
    private String username;

    @NotNull
    @NotBlank(message = "Please, enter the password!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message="Password should have at least 1 number, 1 lower case letter, 1 upper case letter")
    @Size(min = 6, max = 128, message = "Password must be between 6 and 128 symbols")
    private String password;

    @NotNull
    @NotBlank(message = "Please, enter the confirm password!")
    @Size(min = 6, max = 128, message = "Confirm password must be between 6 and 128 symbols")
    private String confirmPassword;
}
