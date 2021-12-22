package movie.web.service;

import movie.web.model.FilmReview;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmReviewService {
    List<FilmReview> getAllFilmReviews();
    Page<FilmReview> getFilmReviewsPaginated(int page, int size);
    Float getFilmAvgScore(Long filmId);
    FilmReview getByFilmIdAndUserId(Long filmId, Long userId);
    List<FilmReview> getFilmReviewsByFilmId(Long filmId);
    Long getFilmReviewsCount();
    FilmReview saveFilmReview(FilmReview filmReview);
    FilmReview leaveScore(FilmReview filmReview);
    FilmReview leaveReview(FilmReview filmReview);
    FilmReview getById(Long id);
    void deleteFilmReviewById(Long id);
    void updateFilmReview(Long id, FilmReview filmReview);
}
