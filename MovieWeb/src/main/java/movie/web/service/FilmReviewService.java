package movie.web.service;

import movie.web.model.FilmReview;

import java.util.List;

public interface FilmReviewService {
    List<FilmReview> getAllFilmReviews();
    FilmReview saveFilmReview(FilmReview filmReview);
    FilmReview getById(Long id);
    void deleteFilmReviewById(Long id);
    void updateFilmReview(Long id, FilmReview filmReview);
}
