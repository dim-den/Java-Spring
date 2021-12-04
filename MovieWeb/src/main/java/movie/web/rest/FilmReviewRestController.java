package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.FilmReviewDTO;
import movie.web.model.FilmReview;
import movie.web.service.FilmReviewService;
import movie.web.service.FilmService;
import movie.web.service.UserService;
import movie.web.service.impl.FilmReviewServiceImpl;
import movie.web.service.impl.FilmServiceImpl;
import movie.web.service.impl.UserServiceImpl;
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
public class FilmReviewRestController {
    private final FilmReviewService filmReviewService;
    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmReviewRestController(FilmReviewServiceImpl filmReviewService,
                                    FilmServiceImpl filmService,
                                    UserServiceImpl userService) {
        this.filmReviewService = filmReviewService;
        this.filmService = filmService;
        this.userService = userService;
    }

    @Operation(summary = "Get all film reviews", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmReviews")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<List<FilmReviewDTO>> getFilmReviews() {
        return ResponseEntity.ok(Mapper.mapAll(filmReviewService.getAllFilmReviews(), FilmReviewDTO.class));
    }

    @Operation(summary = "Save film review", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/filmReview/save")
    @PreAuthorize("hasAuthority('developers:write')")
    void saveFilmReview(@RequestBody @Valid FilmReviewDTO filmReviewDTO) {
        FilmReview filmReview = Mapper.map(filmReviewDTO, FilmReview.class);
        filmReview.setFilm(filmService.getById(filmReviewDTO.getFilmId()));
        filmReview.setUser(userService.getById(filmReviewDTO.getUserId()));

        filmReviewService.saveFilmReview(filmReview);
    }

    @Operation(summary = "Get film review by ID", security = @SecurityRequirement(name = "developers:read"))
    @Loggable
    @GetMapping("/filmReview/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    ResponseEntity<FilmReviewDTO> getFilmReviewById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(Mapper.map(filmReviewService.getById(id), FilmReviewDTO.class));
    }

    @Operation(summary = "Update film review by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PutMapping("/filmReview/update/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void updateFilmReview(@PathVariable @Positive Long id, @RequestBody @Valid FilmReviewDTO filmReviewDTO) {
        FilmReview filmReview = Mapper.map(filmReviewDTO, FilmReview.class);
        filmReview.setFilm(filmService.getById(filmReviewDTO.getFilmId()));
        filmReview.setUser(userService.getById(filmReviewDTO.getUserId()));

        filmReviewService.updateFilmReview(id, Mapper.map(filmReviewDTO, FilmReview.class));
    }

    @Operation(summary = "Delete film review by ID", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @DeleteMapping("/filmReview/delete/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    void deleteFilmReview(@PathVariable @Positive Long id) {
        filmReviewService.deleteFilmReviewById(id);
    }
}
