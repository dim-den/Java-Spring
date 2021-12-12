package movie.web.repository;

import movie.web.model.FilmCast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface FilmCastRepository extends JpaRepository<FilmCast, Long> {
    @Query(nativeQuery = true, value = "SELECT FilmCastPackage.GetFilmCastsCount FROM dual")
    Long getFilmCastsCount();

    @Procedure("FilmCastPackage.AddFilmCast")
    void addFilmCast(@Param("p_ROLE_TYPE") String roleType,
                     @Param("p_ROLE_NAME") String roleName,
                     @Param("p_ACTOR_ID") Long actorId,
                     @Param("p_FILM_ID") Long filmId
    );

    @Procedure("FilmCastPackage.UpdateFilmCast")
    void updateFilmCast(@Param("p_ID") Long id,
                        @Param("p_ROLE_TYPE") String roleType,
                        @Param("p_ROLE_NAME") String roleName,
                        @Param("p_ACTOR_ID") Long actorId,
                        @Param("p_FILM_ID") Long filmId
    );

    @Procedure("FilmCastPackage.DeleteFilmCast")
    void deleteFilmCastById(@Param("p_ID") Long id);
}
