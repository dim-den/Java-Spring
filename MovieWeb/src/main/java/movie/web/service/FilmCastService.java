package movie.web.service;

import movie.web.model.FilmCast;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FilmCastService {
    List<FilmCast> getAllFilmCasts();
    Page<FilmCast> getFilmCastsPaginated(int page, int size);
    Long getFilmCastsCount();
    FilmCast saveFilmCast(FilmCast filmCast);
    FilmCast getById(Long id);
    void deleteFilmCastById(Long id);
    void updateFilmCast(Long id, FilmCast filmCast);
}
