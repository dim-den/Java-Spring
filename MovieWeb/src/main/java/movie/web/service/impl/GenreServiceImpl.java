package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Genre;
import movie.web.repository.GenreRepository;
import movie.web.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl  implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
    
    @Override
    public Page<Genre> getGenresPaginated(int page, int size) {
        return genreRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Long getGenresCount() {
        return genreRepository.getGenresCount();
    }

    @Override
    public Genre saveGenre(Genre Genre) {
        genreRepository.addGenre(Genre.getName());
        return Genre;
    }

    @Override
    public Genre getById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteGenreById(Long id) {
        genreRepository.deleteGenreById(id);
    }

    @Override
    public void updateGenre(Long id, Genre Genre) {
        if(getById(id) != null) {
            genreRepository.updateGenre(id, Genre.getName());
        }
    }
}
