package movie.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class FilmCastDTO {
    private Long id;
    @NotEmpty
    private String roleType;
    @NotEmpty
    private String roleName;
    @Positive
    private Long actorId;
    @Positive
    private Long filmId;
}
