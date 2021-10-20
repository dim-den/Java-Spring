package movie.web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="film_reviews")
public class FilmReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String reviewText;
    private int score;
    private Date published;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
}
