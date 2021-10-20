package movie.web.repository;

import movie.web.model.FilmCast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCastRepository extends JpaRepository<FilmCast, Long> {
}
