package movie.web.unit;

import movie.web.model.Film;
import movie.web.repository.FilmRepository;
import movie.web.service.FilmService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmServiceTest {
    @Autowired
    private FilmService filmService;

    @Test
    public void getByTitle_existingTitle_returnFilm() {
        Film expected = new Film();
        expected.setTitle("Titanic");

        Film actual = filmService.getByTitleContainingIgnoreCase("Black Swan").get(0);

        Assert.assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    public void getByTitle_notExistingTitle_returnNull() {
        Film result = filmService.getByTitleContainingIgnoreCase("NotExisting").get(0);

        Assert.assertNull(result);
    }

    @Test
    public void getAllFilms_returnAllFilms() {
        List<Film> result = filmService.getAllFilms();

        Assert.assertTrue(result != null && result.size() > 0);
    }
}