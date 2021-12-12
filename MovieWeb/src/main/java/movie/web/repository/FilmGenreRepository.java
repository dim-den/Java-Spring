package movie.web.repository;

import movie.web.model.FilmGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface FilmGenreRepository extends JpaRepository<FilmGenre, Long> {
    @Query(nativeQuery = true, value = "SELECT FilmGenrePackage.GetFilmGenresCount FROM dual")
    Long getFilmGenresCount();

    @Procedure("FilmGenrePackage.AddFilmGenre")
    void addFilmGenre(@Param("p_FILM_ID") Long filmId,
                      @Param("p_GENRE_ID") Long genreId
    );

    @Procedure("FilmGenrePackage.UpdateFilmGenre")
    void updateFilmGenre(@Param("p_ID") Long id,
                         @Param("p_FILM_ID") Long filmId,
                         @Param("p_GENRE_ID") Long genreId
    );

    @Procedure("FilmGenrePackage.DeleteFilmGenre")
    void deleteFilmGenreById(@Param("p_ID") Long id);
}

