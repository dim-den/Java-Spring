package movie.web.repository;

import movie.web.model.FilmReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmReviewRepository extends JpaRepository<FilmReview, Long> {
}
