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
    private final FilmReviewRepository FilmReviewRepository;

    @Override
    public List<FilmReview> getAllFilmReviews() {
        return FilmReviewRepository.findAll();
    }

    @Override
    public FilmReview saveFilmReview(FilmReview filmReview) {
        return FilmReviewRepository.save(filmReview);
    }

    @Override
    public FilmReview getById(Long id) {
        return FilmReviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmReviewById(Long id) {
        FilmReviewRepository.deleteById(id);
    }

    @Override
    public void updateFilmReview(Long id, FilmReview filmReview) {
        if(getById(id) != null){
            filmReview.setId(id);
            FilmReviewRepository.save(filmReview);
        }
    }
}
