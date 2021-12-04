package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.FilmCastDTO;
import movie.web.model.FilmCast;
import movie.web.service.ActorService;
import movie.web.service.FilmCastService;
import movie.web.service.FilmService;
import movie.web.service.impl.*;
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
public class FilmCastRestController {
    private final FilmCastService filmCastService;
    private final ActorService actorService;
    private final FilmService filmService;


    @Autowired
    public FilmCastRestController(FilmCastServiceImpl filmCastService,
                                    ActorServiceImpl actorService,
                                    FilmServiceImpl filmService) {
        this.filmCastService = filmCastService;
        this.actorService = actorService;
        this.filmService = filmService;
    }

    @Operation(summary = "Get all film casts", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmCasts")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<List<FilmCastDTO>> getFilmCasts() {
        return ResponseEntity.ok(Mapper.mapAll(filmCastService.getAllFilmCasts(), FilmCastDTO.class));
    }

    @Operation(summary = "Save actor", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/filmCast/save")
    @PreAuthorize("hasAuthority('developers:write')")
    void saveFilmCast(@RequestBody @Valid FilmCastDTO filmCastDTO) {
        FilmCast filmCast = Mapper.map(filmCastDTO, FilmCast.class);
        filmCast.setActor(actorService.getById(filmCastDTO.getActorId()));
        filmCast.setFilm(filmService.getById(filmCastDTO.getFilmId()));

        filmCastService.saveFilmCast(filmCast);
    }

    @Operation(summary = "Get film cast by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmCast/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmCastDTO> getFilmCastById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(filmCastService.getById(id), FilmCastDTO.class));
    }

    @Operation(summary = "Update film cast by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/filmCast/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilmCast(@PathVariable @Positive Long id, @RequestBody @Valid FilmCastDTO filmCastDTO) {
        FilmCast filmCast = Mapper.map(filmCastDTO, FilmCast.class);
        filmCast.setActor(actorService.getById(filmCastDTO.getActorId()));
        filmCast.setFilm(filmService.getById(filmCastDTO.getFilmId()));

        filmCastService.updateFilmCast(id, filmCast);
    }

    @Operation(summary = "Delete film cast by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/filmCast/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilmCast(@PathVariable @Positive Long id) {
        filmCastService.deleteFilmCastById(id);
    }
}
