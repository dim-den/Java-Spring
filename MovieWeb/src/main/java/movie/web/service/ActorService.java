package movie.web.service;

import movie.web.model.Actor;
import movie.web.model.Film;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorService  {
    List<Actor> getAllActors();
    Page<Actor> getActorsPaginated(int page, int size);
    List<Actor> getBySurnameContainingIgnoreCase(String surname);
    Long getActorsCount();
    Actor saveActor(Actor actor);
    Actor getById(Long id);
    void deleteActorById(Long id);
    void updateActor(Long id, Actor actor);

}
