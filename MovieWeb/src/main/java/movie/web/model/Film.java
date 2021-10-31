package movie.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    @Column(name = "release_date")
    private Date release;
    private Long budget;
    private Long fees;

    @OneToMany(mappedBy = "film")
    private Set<FilmReview> filmReviews;

    @OneToMany(mappedBy = "film")
    private Set<FilmCast> filmCasts;
}
