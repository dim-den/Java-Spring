package movie.web.rest;

import movie.web.model.FilmCast;
import movie.web.service.FilmCastService;
import movie.web.service.impl.FilmCastServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class FilmCastRestController {
    private final FilmCastService filmCastService;

    @Autowired
    public FilmCastRestController(FilmCastServiceImpl filmCastService) {
        this.filmCastService = filmCastService;
    }

    @GetMapping("/filmCasts")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmCast>> getFilmCasts() {
        return ResponseEntity.ok(filmCastService.getAllFilmCasts());
    }

    @PostMapping("/filmCast/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<FilmCast> saveFilmCast(@RequestBody FilmCast filmCast) {
        return ResponseEntity.ok(filmCastService.saveFilmCast(filmCast));
    }

    @GetMapping("/filmCast/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmCast> getFilmCastById(@PathVariable Long id) {
        return ResponseEntity.ok(filmCastService.getByID(id));
    }

    @PutMapping("/filmCast/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilmCast(@PathVariable Long id, @RequestBody FilmCast filmCast) {
        filmCastService.updateFilmCast(id, filmCast);
    }

    @DeleteMapping("/filmCast/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilmCast(@PathVariable Long id) {
        filmCastService.deleteFilmCastById(id);
    }
}
