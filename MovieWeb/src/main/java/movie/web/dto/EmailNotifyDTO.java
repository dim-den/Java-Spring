package movie.web.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailNotifyDTO {
    @Email
    private String email;
    private String topic = "Upcoming films";
    private int upcomingInDays = 7;
}
