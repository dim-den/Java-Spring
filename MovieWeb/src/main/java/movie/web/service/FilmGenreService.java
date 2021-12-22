package movie.web.service;

import movie.web.model.FilmGenre;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmGenreService {
    List<FilmGenre> getAllFilmGenres();
    Page<FilmGenre> getFilmGenresPaginated(int page, int size);
    List<FilmGenre> getByFilmId(Long id);
    Long getFilmGenresCount();
    FilmGenre saveFilmGenre(FilmGenre FilmGenre);
    FilmGenre getById(Long id);
    void deleteFilmGenreById(Long id);
    void updateFilmGenre(Long id, FilmGenre FilmGenre);
}
