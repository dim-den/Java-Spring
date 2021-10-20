package movie.web.rest;

import movie.web.model.Actor;
import movie.web.model.User;
import movie.web.service.ActorService;
import movie.web.service.UserService;
import movie.web.service.impl.ActorServiceImpl;
import movie.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class ActorRestController {
    private final ActorService actorService;

    @Autowired
    public ActorRestController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Actor>> getActors() {
        return ResponseEntity.ok(actorService.getAllActors());
    }

    @PostMapping("/actor/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Actor> saveActor(@RequestBody Actor actor) {
        return ResponseEntity.ok(actorService.saveActor(actor));
    }

    @GetMapping("/actor/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<Actor> getActorById(@PathVariable Long id) {
        return ResponseEntity.ok(actorService.getByID(id));
    }

    @PutMapping("/actor/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateActor(@PathVariable Long id, @RequestBody Actor actor) {
        actorService.updateActor(id, actor);
    }

    @DeleteMapping("/actor/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteActor(@PathVariable Long id) {
        actorService.deleteActorById(id);
    }
}
