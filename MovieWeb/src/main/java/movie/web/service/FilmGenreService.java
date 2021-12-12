package movie.web.service;

import movie.web.model.FilmGenre;

import java.util.List;

public interface FilmGenreService {
    List<FilmGenre> getAllFilmGenres();
    FilmGenre saveFilmGenre(FilmGenre FilmGenre);
    FilmGenre getById(Long id);
    void deleteFilmGenreById(Long id);
    void updateFilmGenre(Long id, FilmGenre FilmGenre);
}
