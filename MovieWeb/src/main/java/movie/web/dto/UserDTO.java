package movie.web.dto;

import lombok.Data;
import movie.web.model.Role;
import movie.web.model.Status;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    private Long id;
    @Email
    private String email;
    @NotEmpty
    @Length(min = 4, max = 15)
    private String username;
    @NotEmpty
    private String passwordHash;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
