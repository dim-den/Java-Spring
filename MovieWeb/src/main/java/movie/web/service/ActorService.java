package movie.web.service;

import movie.web.model.Actor;

import java.util.List;

public interface ActorService  {
    List<Actor> getAllActors();
    Actor saveActor(Actor actor);
    Actor getByID(Long id);
    void deleteActorById(Long id);
    void updateActor(Long id, Actor actor);
}
