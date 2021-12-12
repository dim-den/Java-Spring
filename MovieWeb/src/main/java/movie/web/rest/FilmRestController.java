package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.ActorDTO;
import movie.web.dto.FilmDTO;
import movie.web.model.Film;
import movie.web.service.FilmService;
import movie.web.service.impl.FilmServiceImpl;
import movie.web.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FilmRestController {
    private final FilmService filmService;

    @Autowired
    public FilmRestController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @Operation(summary = "Get all films", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/films")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmDTO>> getFilms() {
        return ResponseEntity.ok(Mapper.mapAll(filmService.getAllFilms(), FilmDTO.class));
    }

    @Operation(summary = "Get page of films", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping(value = "/films", params = { "page", "size" })
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmDTO>> getFilmsPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(Mapper.mapAll(filmService.getFilmsPaginated(page, size).toList(), FilmDTO.class));
    }

    @Operation(summary = "Get count of films", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/films/count")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getFilmsCount() {
        return ResponseEntity.ok(filmService.getFilmsCount());
    }


    @Operation(summary = "Save film", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/film/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public void saveFilm(@Valid @RequestBody FilmDTO filmDTO) {
        filmService.saveFilm(Mapper.map(filmDTO, Film.class));
    }

    @Operation(summary = "Get film by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/film/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmDTO> getFilmById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(filmService.getById(id), FilmDTO.class));
    }

    @Operation(summary = "Get film by title name", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/film")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmDTO> getFilmByTitle(@RequestParam @NotNull String title) {
        return ResponseEntity.ok(Mapper.map(filmService.getByTitle(title), FilmDTO.class));
    }

    @Operation(summary = "Update film by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/film/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilm(@PathVariable Long id, @RequestBody @Valid FilmDTO filmDTO) {
        filmService.updateFilm(id, Mapper.map(filmDTO, Film.class));
    }

    @Operation(summary = "Delete film by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/film/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilm(@PathVariable @Positive Long id) {
        filmService.deleteFilmById(id);
    }
}
