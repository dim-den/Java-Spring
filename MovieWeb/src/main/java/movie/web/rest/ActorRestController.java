package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.ActorDTO;
import movie.web.model.Actor;
import movie.web.model.Role;
import movie.web.service.ActorService;
import movie.web.service.impl.ActorServiceImpl;
import movie.web.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Operation(summary = "Get all actors", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/actors")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<ActorDTO>> getActors() {
        return ResponseEntity.ok(Mapper.mapAll(actorService.getAllActors(), ActorDTO.class));
    }

    @Operation(summary = "Get page of actors", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping(value = "/actors", params = { "page", "size" })
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<ActorDTO>> getActorsPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(Mapper.mapAll(actorService.getActorsPaginated(page, size).toList(), ActorDTO.class));
    }

    @Operation(summary = "Get actor by surname", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/actor")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<List<ActorDTO>> getActorBySurname(@RequestParam @NotNull String surname) {
        return ResponseEntity.ok(Mapper.mapAll(actorService.getBySurnameContainingIgnoreCase(surname), ActorDTO.class));
    }

    @Operation(summary = "Get count of actors", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/actors/count")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getActorsCount() {
        return ResponseEntity.ok(actorService.getActorsCount());
    }

    @Operation(summary = "Save actor", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/actor/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public void saveActor(@Valid @RequestBody ActorDTO actorDTO) {
        actorService.saveActor(Mapper.map(actorDTO, Actor.class));
    }

    @Operation(summary = "Get actor by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/actor/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<ActorDTO> getActorById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(actorService.getById(id), ActorDTO.class));
    }

    @Operation(summary = "Update actor by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/actor/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateActor(@PathVariable @Positive Long id, @Valid @RequestBody ActorDTO actorDTO) {
        actorService.updateActor(id, Mapper.map(actorDTO, Actor.class));
    }

    @Operation(summary = "Delete actor by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/actor/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteActor(@PathVariable @Positive Long id) {
        actorService.deleteActorById(id);
    }
}