package movie.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name="FILM")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany(mappedBy = "film")
    private Set<FilmGenre> filmGenres;

    private String description;
    private String director;
    private String country;
    private Date release;
    private Long budget;
    private Long fees;

    @OneToMany(mappedBy = "film")
    private Set<FilmReview> filmReviews;

    @OneToMany(mappedBy = "film")
    private Set<FilmCast> filmCasts;
}
