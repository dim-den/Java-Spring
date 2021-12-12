package movie.web.repository;

import movie.web.model.FilmReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface FilmReviewRepository extends JpaRepository<FilmReview, Long> {
    @Query(nativeQuery = true, value = "SELECT FilmReviewPackage.GetFilmReviewsCount FROM dual")
    Long getFilmReviewsCount();

    @Procedure("FilmReviewPackage.AddFilmReview")
    void addFilmReview(@Param("p_REVIEW") String review,
                       @Param("p_SCORE") int score,
                       @Param("p_PUBLISHED") Date published,
                       @Param("p_FILM_ID") Long filmId,
                       @Param("p_USER_ID") Long userId
    );

    @Procedure("FilmReviewPackage.UpdateFilmReview")
    void updateFilmReview(@Param("p_ID") Long id,
                          @Param("p_REVIEW") String review,
                          @Param("p_SCORE") int score,
                          @Param("p_PUBLISHED") Date published,
                          @Param("p_FILM_ID") Long filmId,
                          @Param("p_USER_ID") Long userId
    );

    @Procedure("FilmReviewPackage.DeleteFilmReview")
    void deleteFilmReviewById(@Param("p_ID") Long id);

}
