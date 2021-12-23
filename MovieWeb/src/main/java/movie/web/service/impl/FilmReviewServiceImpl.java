package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Film;
import movie.web.model.FilmReview;
import movie.web.repository.FilmReviewRepository;
import movie.web.service.FilmReviewService;
import movie.web.util.FilmReviewValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmReviewServiceImpl implements FilmReviewService {
    private final FilmReviewRepository filmReviewRepository;
    private final FilmReviewValidator filmReviewValidator;

    @Override
    public List<FilmReview> getAllFilmReviews() {
        return filmReviewRepository.findAll();
    }

    @Override
    public Page<FilmReview> getFilmReviewsPaginated(int page, int size) {
        return filmReviewRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Float getFilmAvgScore(Long filmId) {
        return filmReviewRepository.getFilmAvgScore(filmId);
    }

    @Override
    public FilmReview getByFilmIdAndUserId(Long filmId, Long userId) {
        return filmReviewRepository.getByFilmIdAndUserId(filmId, userId);
    }

    @Override
    public List<FilmReview> getFilmReviewsByFilmId(Long filmId) {
        List<FilmReview> filmReviews = filmReviewRepository.getByFilmId(filmId);
        filmReviews.removeIf(filmReview -> filmReview.getReview() == null || filmReview.getReview().length() == 0);

        return filmReviews;
    }

    @Override
    public Long getFilmReviewsCount() {
        return filmReviewRepository.getFilmReviewsCount();
    }

    @Override
    public FilmReview saveFilmReview(FilmReview filmReview) {
        filmReviewRepository.addFilmReview(
                filmReview.getReview(),
                filmReview.getScore(),
                filmReview.getPublished(),
                filmReview.getFilm().getId(),
                filmReview.getUser().getId());
        return filmReview;
    }

    @Override
    public FilmReview leaveScore(FilmReview filmReview) {
        FilmReview existingFilmReview = filmReviewRepository.getByFilmIdAndUserId(filmReview.getFilm().getId(),
                                                                                  filmReview.getUser().getId());

        if(existingFilmReview == null) {
            saveFilmReview(filmReview);
        }
        else {
            existingFilmReview.setScore(filmReview.getScore());
            if(existingFilmReview.getReview() == null) existingFilmReview.setReview("");
            updateFilmReview(existingFilmReview.getId(), existingFilmReview);
        }

        return filmReview;
    }

    @Override
    public FilmReview leaveReview(FilmReview filmReview) {
        DataBinder binder = new DataBinder(filmReview);
        binder.setValidator(filmReviewValidator);
        binder.validate();
        BindingResult results = binder.getBindingResult();
        if(!results.hasErrors()) {
            FilmReview existingFilmReview = filmReviewRepository.getByFilmIdAndUserId(filmReview.getFilm().getId(),
                    filmReview.getUser().getId());

            if (existingFilmReview == null) {
                saveFilmReview(filmReview);
            } else {
                existingFilmReview.setScore(filmReview.getScore());
                existingFilmReview.setReview(filmReview.getReview());
                updateFilmReview(existingFilmReview.getId(), existingFilmReview);
            }
        }
        return filmReview;
    }

    @Override
    public FilmReview getById(Long id) {
        return filmReviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmReviewById(Long id) {
        filmReviewRepository.deleteFilmReviewById(id);
    }

    @Override
    public void updateFilmReview(Long id, FilmReview filmReview) {
        if(getById(id) != null){
            filmReviewRepository.updateFilmReview(id,
                    filmReview.getReview(),
                    filmReview.getScore(),
                    filmReview.getPublished(),
                    filmReview.getFilm().getId(),
                    filmReview.getUser().getId());
        }
    }
}
