package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Film;
import movie.web.repository.FilmRepository;
import movie.web.service.FilmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Film getByTitle(String title) {
        return filmRepository.findByTitle(title).orElse(null);
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
