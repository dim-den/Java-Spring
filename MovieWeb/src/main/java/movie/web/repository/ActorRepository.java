package movie.web.repository;

import movie.web.model.Actor;
import movie.web.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Procedure("ActorPackage.AddActor")
    void addActor(@Param("p_NAME") String name,
                 @Param("p_SURNAME") String surname,
                 @Param("p_COUNTRY") String country,
                 @Param("p_BDAY") Date bday
    );

    @Query(nativeQuery = true, value = "SELECT ActorPackage.GetActorsCount FROM dual")
    Long getActorsCount();

    @Procedure("ActorPackage.UpdateActor")
    void updateActor(@Param("p_ID") Long id,
                     @Param("p_NAME") String name,
                     @Param("p_SURNAME") String surname,
                     @Param("p_COUNTRY") String country,
                     @Param("p_BDAY") Date bday
    );

    @Procedure("ActorPackage.DeleteActor")
    void deleteActorById(@Param("p_ID") Long id);

    List<Actor> findTop5BySurnameContainingIgnoreCase(String surname);
}
