package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Film;
import movie.web.model.FilmReview;
import movie.web.repository.FilmRepository;
import movie.web.service.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Page<Film> getFilmsPaginated(int page, int size) {
        return filmRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Long getFilmsCount() {
        return filmRepository.getFilmsCount();
    }

    @Override
    public Film saveFilm(Film film) {
        filmRepository.addFilm(
                film.getTitle(),
                film.getDescription(),
                film.getDirector(),
                film.getCountry(),
                film.getRelease(),
                film.getBudget(),
                film.getFees());
        return film;
    }

    @Override
    public Film getById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @Override
    public List<Film> getByTitleContainingIgnoreCase(String title) {
        return filmRepository.findTop5ByTitleContainingIgnoreCase(title);
    }

    @Override
    public void deleteFilmById(Long id) {
        filmRepository.deleteFilmById(id);
    }

    @Override
    public void updateFilm(Long id, Film film) {
        if (getById(id) != null) {
            filmRepository.updateFilm(id,
                    film.getTitle(),
                    film.getDescription(),
                    film.getDirector(),
                    film.getCountry(),
                    film.getRelease(),
                    film.getBudget(),
                    film.getFees());
        }
    }
}
