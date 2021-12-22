package movie.web.dto;

import lombok.Data;
import movie.web.model.User;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
public class FilmReviewDTO {
    private Long id;
    private String review;
    @Range(min = 1, max = 10, message = "Score should be between 1 and 10")
    private int score;
    @NotNull
    @DateTimeFormat()
    private Date published;
    @Positive
    private Long userId;
    @Positive
    private Long filmId;
}
