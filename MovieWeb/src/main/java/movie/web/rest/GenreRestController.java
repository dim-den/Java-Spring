package movie.web.rest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.ActorDTO;
import movie.web.dto.GenreDTO;
import movie.web.model.Genre;
import movie.web.model.Role;
import movie.web.service.GenreService;
import movie.web.service.impl.GenreServiceImpl;
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
public class GenreRestController {
    private final GenreService genreService;

    @Autowired
    public GenreRestController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @Operation(summary = "Get all genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/genres")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<GenreDTO>> getGenres() {
        return ResponseEntity.ok(Mapper.mapAll(genreService.getAllGenres(), GenreDTO.class));
    }

    @Operation(summary = "Get page of genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping(value = "/genres", params = { "page", "size" })
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<GenreDTO>> getGenresPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(Mapper.mapAll(genreService.getGenresPaginated(page, size).toList(), GenreDTO.class));
    }

    @Operation(summary = "Get count of genres", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/genres/count")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<Long> getGenresCount() {
        return ResponseEntity.ok(genreService.getGenresCount());
    }

    @Operation(summary = "Save Genre", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/genre/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public void saveGenre(@Valid @RequestBody GenreDTO GenreDTO) {
        genreService.saveGenre(Mapper.map(GenreDTO, Genre.class));
    }

    @Operation(summary = "Get Genre by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/genre/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<GenreDTO> getGenreById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(genreService.getById(id), GenreDTO.class));
    }

    @Operation(summary = "Update Genre by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/genre/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateGenre(@PathVariable @Positive Long id, @Valid @RequestBody GenreDTO GenreDTO) {
        genreService.updateGenre(id, Mapper.map(GenreDTO, Genre.class));
    }

    @Operation(summary = "Delete Genre by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/genre/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteGenre(@PathVariable @Positive Long id) {
        genreService.deleteGenreById(id);
    }
}
