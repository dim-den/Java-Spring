package movie.web.service;

import movie.web.model.FilmCast;

import java.util.List;

public interface FilmCastService {
    List<FilmCast> getAllFilmCasts();
    FilmCast saveFilmCast(FilmCast filmCast);
    FilmCast getById(Long id);
    void deleteFilmCastById(Long id);
    void updateFilmCast(Long id, FilmCast filmCast);
}
