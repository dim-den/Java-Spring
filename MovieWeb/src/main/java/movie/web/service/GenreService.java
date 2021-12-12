package movie.web.service;

import movie.web.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    Genre saveGenre(Genre genre);
    Genre getById(Long id);
    void deleteGenreById(Long id);
    void updateGenre(Long id, Genre genre);
}
