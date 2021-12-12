package movie.web.repository;

import movie.web.model.Film;
import movie.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Procedure("FilmPackage.AddFilm")
    void addFilm(@Param("p_Title") String title,
                 @Param("p_Description") String description,
                 @Param("p_Director") String director,
                 @Param("p_Country") String country,
                 @Param("p_Release") Date release,
                 @Param("p_Budget") Long budget,
                 @Param("p_Fees") Long fees
                 );

    @Procedure("FilmPackage.UpdateFilm")
    void updateFilm(@Param("p_ID") Long id,
                    @Param("p_Title") String title,
                    @Param("p_Description") String description,
                    @Param("p_Director") String director,
                    @Param("p_Country") String country,
                    @Param("p_Release") Date release,
                    @Param("p_Budget") Long budget,
                    @Param("p_Fees") Long fees
    );

    @Procedure("FilmPackage.DeleteFilm")
    void deleteFilmById(@Param("p_ID") Long id);

    Optional<Film> findByTitle(String title);
}
