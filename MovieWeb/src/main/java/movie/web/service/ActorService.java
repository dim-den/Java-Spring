package movie.web.service;

import movie.web.model.Actor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActorService  {
    List<Actor> getAllActors();
    Page<Actor> getActorsPaginated(int page, int size);
    Long getActorsCount();
    Actor saveActor(Actor actor);
    Actor getById(Long id);
    void deleteActorById(Long id);
    void updateActor(Long id, Actor actor);

}
