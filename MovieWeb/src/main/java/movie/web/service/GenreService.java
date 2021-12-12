package movie.web.service;

import movie.web.model.Genre;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
    Page<Genre> getGenresPaginated(int page, int size);
    Long getGenresCount();
    Genre saveGenre(Genre genre);
    Genre getById(Long id);
    void deleteGenreById(Long id);
    void updateGenre(Long id, Genre genre);
}
