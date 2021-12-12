package movie.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class GenreDTO {
    private Long id;
    @NotBlank
    private String name;
}