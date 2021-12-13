package movie.web.service;

import movie.web.model.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    Page<Film> getFilmsPaginated(int page, int size);
    Long getFilmsCount();
    Film saveFilm(Film film);
    Film getById(Long id);
    List<Film> getByTitleContainingIgnoreCase(String title);
    void deleteFilmById(Long id);
    void updateFilm(Long id, Film film);
}
