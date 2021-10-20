package movie.web.service;

import movie.web.model.Film;
import movie.web.model.User;

import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    Film saveFilm(Film film);
    Film getByID(Long id);
    Film getByTitle(String title);
    void deleteFilmById(Long id);
    void updateFilm(Long id, Film film);
}
