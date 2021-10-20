package movie.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FilmReviewDto {
    private Long id;
    private String review_text;
    private int score;
    private Date published;
    private Long user_id;
    private Long film_id;
}
