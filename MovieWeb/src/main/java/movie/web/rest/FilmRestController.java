package movie.web.rest;

import movie.web.model.Film;
import movie.web.model.User;
import movie.web.service.FilmService;
import movie.web.service.UserService;
import movie.web.service.impl.FilmServiceImpl;
import movie.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class FilmRestController {
    private final FilmService filmService;

    @Autowired
    public FilmRestController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<Film>> getFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @PostMapping("/film/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<Film> saveFilm(@RequestBody Film film) {
        return ResponseEntity.ok(filmService.saveFilm(film));
    }

    @GetMapping("/film/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return ResponseEntity.ok(filmService.getByID(id));
    }

    @GetMapping("/film")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<Film> getFilmByTitle(@RequestParam String title) {
        return ResponseEntity.ok(filmService.getByTitle(title));
    }

    @PutMapping("/film/update/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    void updateFilm(@PathVariable Long id, @RequestBody Film film) {
        filmService.updateFilm(id, film);
    }

    @DeleteMapping("/film/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilm(@PathVariable Long id) {
        filmService.deleteFilmById(id);
    }
}
