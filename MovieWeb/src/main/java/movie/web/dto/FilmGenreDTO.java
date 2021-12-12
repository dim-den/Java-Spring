package movie.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class FilmGenreDTO {
    private Long id;
    @Positive
    private Long filmId;
    @Positive
    private Long genreId;
}
