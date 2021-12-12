package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.FilmCastDTO;
import movie.web.dto.FilmGenreDTO;
import movie.web.model.FilmGenre;
import movie.web.model.Role;
import movie.web.service.FilmGenreService;
import movie.web.service.impl.FilmGenreServiceImpl;
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
public class FilmGenreRestController {
    private final FilmGenreService filmGenreService;

    @Autowired
    public FilmGenreRestController(FilmGenreServiceImpl FilmGenreService) {
        this.filmGenreService = FilmGenreService;
    }

    @Operation(summary = "Get all film genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmGenres")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmGenreDTO>> getFilmGenres() {
        return ResponseEntity.ok(Mapper.mapAll(filmGenreService.getAllFilmGenres(), FilmGenreDTO.class));
    }

    @Operation(summary = "Get page of film genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping(value = "/filmGenres", params = { "page", "size" })
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmGenreDTO>> getFilmGenresPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(Mapper.mapAll(filmGenreService.getFilmGenresPaginated(page, size).toList(), FilmGenreDTO.class));
    }

    @Operation(summary = "Get count of film genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmGenres/count")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getFilmGenresCount() {
        return ResponseEntity.ok(filmGenreService.getFilmGenresCount());
    }

    @Operation(summary = "Save film genre", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/filmGenre/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public void saveFilmGenre(@Valid @RequestBody FilmGenreDTO FilmGenreDTO) {
        filmGenreService.saveFilmGenre(Mapper.map(FilmGenreDTO, FilmGenre.class));
    }

    @Operation(summary = "Get film genre by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmGenre/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmGenreDTO> getFilmGenreById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(filmGenreService.getById(id), FilmGenreDTO.class));
    }

    @Operation(summary = "Update film genre by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/filmGenre/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilmGenre(@PathVariable @Positive Long id, @Valid @RequestBody FilmGenreDTO FilmGenreDTO) {
        filmGenreService.updateFilmGenre(id, Mapper.map(FilmGenreDTO, FilmGenre.class));
    }

    @Operation(summary = "Delete film genre by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/filmGenre/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilmGenre(@PathVariable @Positive Long id) {
        filmGenreService.deleteFilmGenreById(id);
    }
}
