package movie.web.service.impl;

import lombok.RequiredArgsConstructor;
import movie.web.model.Actor;
import movie.web.repository.ActorRepository;
import movie.web.service.ActorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Page<Actor> getActorsPaginated(int page, int size) {
        return actorRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Actor> getBySurnameContainingIgnoreCase(String surname) {
        return actorRepository.findTop5BySurnameContainingIgnoreCase(surname);
    }

    @Override
    public Long getActorsCount() {
        return actorRepository.getActorsCount();
    }

    @Override
    public Actor saveActor(Actor actor) {
        actorRepository.addActor(
                actor.getName(),
                actor.getSurname(),
                actor.getCountry(),
                actor.getBday());
        return actor;
    }

    @Override
    public Actor getById(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteActorById(Long id) {
        actorRepository.deleteActorById(id);
    }

    @Override
    public void updateActor(Long id, Actor actor) {
        if(getById(id) != null) {
            actorRepository.updateActor(id,
                    actor.getName(),
                    actor.getSurname(),
                    actor.getCountry(),
                    actor.getBday());
        }
    }
}
