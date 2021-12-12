package movie.web.service;

import movie.web.model.FilmReview;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmReviewService {
    List<FilmReview> getAllFilmReviews();
    Page<FilmReview> getFilmReviewsPaginated(int page, int size);
    Long getFilmReviewsCount();
    FilmReview saveFilmReview(FilmReview filmReview);
    FilmReview getById(Long id);
    void deleteFilmReviewById(Long id);
    void updateFilmReview(Long id, FilmReview filmReview);
}
