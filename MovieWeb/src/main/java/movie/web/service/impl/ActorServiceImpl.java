package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Actor;
import movie.web.repository.ActorRepository;
import movie.web.service.ActorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorServiceImpl implements ActorService {
    private final ActorRepository ActorRepository;

    @Override
    public List<Actor> getAllActors() {
        return ActorRepository.findAll();
    }

    @Override
    public Actor saveActor(Actor actor) {
        return ActorRepository.save(actor);
    }

    @Override
    public Actor getById(Long id) {
        return ActorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteActorById(Long id) {
        ActorRepository.deleteById(id);
    }

    @Override
    public void updateActor(Long id, Actor actor) {
        if(getById(id) != null) {
            actor.setId(id);
            ActorRepository.save(actor);
        }
    }
}
