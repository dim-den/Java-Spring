package movie.web.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class FilmDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String genre;
    @NotEmpty
    private String description;
    @NotEmpty
    private String director;
    @NotEmpty
    private String country;
    @NotNull
    private Date release;
    @Min(1)
    private Long budget;
    @Min(1)
    private Long fees;
}
