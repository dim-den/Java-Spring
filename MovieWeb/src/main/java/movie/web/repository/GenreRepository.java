package movie.web.repository;

import movie.web.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(nativeQuery = true, value = "SELECT GenrePackage.GetGenresCount FROM dual")
    Long getGenresCount();

    @Procedure("GenrePackage.AddGenre")
    void addGenre(@Param("p_NAME") String name);

    @Procedure("GenrePackage.UpdateGenre")
    void updateGenre(@Param("p_ID") Long id,
                     @Param("p_NAME") String name
    );

    @Procedure("GenrePackage.DeleteGenre")
    void deleteGenreById(@Param("p_ID") Long id);

}
