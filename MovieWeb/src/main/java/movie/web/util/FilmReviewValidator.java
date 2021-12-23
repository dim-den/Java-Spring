package movie.web.util;

import movie.web.model.FilmReview;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FilmReviewValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FilmReview.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FilmReview filmReview = (FilmReview) target;

        if(filmReview.getReview().length() <= 10 || filmReview.getReview().length() > 1024)
            errors.rejectValue("review", "Wrong length of review (min 10)");

        if(filmReview.getScore() < 0 || filmReview.getScore() > 10)
            errors.rejectValue("review", "Score should be between 1 and 10");
    }
}
