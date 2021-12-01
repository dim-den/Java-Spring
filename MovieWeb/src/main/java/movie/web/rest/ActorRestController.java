package movie.web.rest;

import movie.web.aop.Loggable;
import movie.web.dto.ActorDTO;
import movie.web.model.Actor;
import movie.web.service.ActorService;
import movie.web.service.impl.ActorServiceImpl;
import movie.web.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RequestMapping("/api")
@RestController
public class ActorRestController {
    private final ActorService actorService;

    @Autowired
    public ActorRestController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @Loggable
    @GetMapping("/actors")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<ActorDTO>> getActors() {
        return ResponseEntity.ok(Mapper.mapAll(actorService.getAllActors(), ActorDTO.class));
    }

    @Loggable
    @PostMapping("/actor/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public void saveActor(@Valid @RequestBody ActorDTO actorDTO) {
        actorService.saveActor(Mapper.map(actorDTO, Actor.class));
    }

    @Loggable
    @GetMapping("/actor/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<ActorDTO> getActorById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(actorService.getById(id), ActorDTO.class));
    }

    @Loggable
    @PutMapping("/actor/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateActor(@PathVariable @Positive Long id, @Valid @RequestBody ActorDTO actorDTO) {
        actorService.updateActor(id, Mapper.map(actorDTO, Actor.class));
    }

    @Loggable
    @DeleteMapping("/actor/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteActor(@PathVariable @Positive Long id) {
        actorService.deleteActorById(id);
    }
}
