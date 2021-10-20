package movie.web.repository;

import movie.web.model.Film;
import movie.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    Optional<Film> findByTitle(String title);
}
