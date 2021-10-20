package movie.web.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="film_casts")
public class FilmCast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String roleType;
    private String roleName;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "actor_id")
    private Actor actor;
}
