package movie.web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name="films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String genre;
    private String description;
    private String director;
    private String country;
    private Date releaseDate;
    private Long budget;
    private Long Fees;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "film")
    private Set<FilmReview> filmReviews;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "film")
    private Set<FilmCast> filmCasts;
}
