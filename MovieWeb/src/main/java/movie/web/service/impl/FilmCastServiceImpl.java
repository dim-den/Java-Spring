package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.FilmCast;
import movie.web.repository.FilmCastRepository;
import movie.web.service.FilmCastService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmCastServiceImpl implements FilmCastService {
    private final FilmCastRepository FilmCastRepository;

    @Override
    public List<FilmCast> getAllFilmCasts() {
        return FilmCastRepository.findAll();
    }

    @Override
    public FilmCast saveFilmCast(FilmCast filmCast) {
        return FilmCastRepository.save(filmCast);
    }

    @Override
    public FilmCast getById(Long id) {
        return FilmCastRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmCastById(Long id) {
        FilmCastRepository.deleteById(id);
    }

    @Override
    public void updateFilmCast(Long id, FilmCast filmCast) {
        if(getById(id) != null) {
            filmCast.setId(id);
            FilmCastRepository.save(filmCast);
        }
    }
}
