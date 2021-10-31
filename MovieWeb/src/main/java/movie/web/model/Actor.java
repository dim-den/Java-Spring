package movie.web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name="actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String country;
    private Date bday;

    @OneToMany(mappedBy = "actor")
    private Set<FilmCast> filmCasts;
}
