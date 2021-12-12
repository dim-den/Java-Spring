package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.FilmReview;
import movie.web.repository.FilmReviewRepository;
import movie.web.service.FilmReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmReviewServiceImpl implements FilmReviewService {
    private final FilmReviewRepository filmReviewRepository;

    @Override
    public List<FilmReview> getAllFilmReviews() {
        return filmReviewRepository.findAll();
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
