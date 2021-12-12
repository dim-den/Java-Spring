package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.FilmGenre;
import movie.web.repository.FilmGenreRepository;
import movie.web.service.FilmGenreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmGenreServiceImpl implements FilmGenreService {
    private final FilmGenreRepository filmGenreRepository;

    @Override
    public List<FilmGenre> getAllFilmGenres() {
        return filmGenreRepository.findAll();
    }

    @Override
    public FilmGenre saveFilmGenre(FilmGenre filmGenre) {
        filmGenreRepository.addFilmGenre(
                filmGenre.getFilm().getId(),
                filmGenre.getGenre().getId());
        return filmGenre;
    }

    @Override
    public FilmGenre getById(Long id) {
        return filmGenreRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmGenreById(Long id) {
        filmGenreRepository.deleteFilmGenreById(id);
    }

    @Override
    public void updateFilmGenre(Long id, FilmGenre filmGenre) {
        if(getById(id) != null) {
            filmGenreRepository.updateFilmGenre(id,
                    filmGenre.getFilm().getId(),
                    filmGenre.getGenre().getId());
        }
    }
}
