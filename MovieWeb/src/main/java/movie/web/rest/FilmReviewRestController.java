package movie.web.rest;

import movie.web.dto.FilmReviewDto;
import movie.web.model.FilmReview;
import movie.web.model.User;
import movie.web.service.FilmReviewService;
import movie.web.service.UserService;
import movie.web.service.impl.FilmReviewServiceImpl;
import movie.web.service.impl.UserServiceImpl;
import movie.web.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class FilmReviewRestController {
    private final FilmReviewService filmReviewService;

    @Autowired
    public FilmReviewRestController(FilmReviewServiceImpl filmReviewService) {
        this.filmReviewService = filmReviewService;
    }

    @GetMapping("/filmReviews")
    @PreAuthorize("hasAuthority('developers:read')")
    public ResponseEntity<List<FilmReview>> getFilmReviews() {
        return ResponseEntity.ok(filmReviewService.getAllFilmReviews());
    }

    @PostMapping("/filmReview/save")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResponseEntity<FilmReview> saveFilmReview(@RequestBody FilmReview filmReview) {
        return ResponseEntity.ok(filmReviewService.saveFilmReview(filmReview));
    }

    @GetMapping("/filmReview/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmReview> getFilmReviewById(@PathVariable Long id) {
        return ResponseEntity.ok(filmReviewService.getByID(id));
    }

    @PutMapping("/filmReview/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilmReview(@PathVariable Long id, @RequestBody FilmReview filmReview) {
        filmReviewService.updateFilmReview(id, filmReview);
    }

    @DeleteMapping("/filmReview/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilmReview(@PathVariable Long id) {
        filmReviewService.deleteFilmReviewById(id);
    }
}
