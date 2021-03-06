package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.FilmCast;
import movie.web.repository.FilmCastRepository;
import movie.web.service.FilmCastService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmCastServiceImpl implements FilmCastService {
    private final FilmCastRepository filmCastRepository;

    @Override
    public List<FilmCast> getAllFilmCasts() {
        return filmCastRepository.findAll();
    }

    @Override
    public Page<FilmCast> getFilmCastsPaginated(int page, int size) {
        return filmCastRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Long getFilmCastsCount() {
        return filmCastRepository.getFilmCastsCount();
    }


    @Override
    public FilmCast saveFilmCast(FilmCast filmCast) {
        filmCastRepository.addFilmCast(
                filmCast.getRoleType(),
                filmCast.getRoleName(),
                filmCast.getActor().getId(),
                filmCast.getFilm().getId());
        return filmCast;
    }

    @Override
    public FilmCast getById(Long id) {
        return filmCastRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteFilmCastById(Long id) {
        filmCastRepository.deleteFilmCastById(id);
    }

    @Override
    public void updateFilmCast(Long id, FilmCast filmCast) {
        if(getById(id) != null) {
            filmCastRepository.updateFilmCast(id,
                    filmCast.getRoleType(),
                    filmCast.getRoleName(),
                    filmCast.getActor().getId(),
                    filmCast.getFilm().getId());
        }
    }

    @Override
    public List<FilmCast> getByActorId(Long actorId) {
        return filmCastRepository.getByActorId(actorId);
    }
}
