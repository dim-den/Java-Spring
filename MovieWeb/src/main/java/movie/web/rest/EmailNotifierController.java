package movie.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import movie.web.aop.Loggable;
import movie.web.dto.EmailNotifyDTO;
import movie.web.model.User;
import movie.web.service.EmailService;
import movie.web.service.FilmService;
import movie.web.service.UserService;
import movie.web.service.impl.EmailServiceImpl;
import movie.web.service.impl.FilmServiceImpl;
import movie.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Validated
@RestController
@RequestMapping("/api/email")
public class EmailNotifierController {
    private EmailService emailService;
    private UserService userService;
    private FilmService filmService;

    @Autowired
    public EmailNotifierController(EmailServiceImpl emailService, UserServiceImpl userService, FilmServiceImpl filmService) {
        this.emailService = emailService;
        this.userService = userService;
        this.filmService = filmService;
    }

    @Operation(summary = "Notify user about upcoming films", security = @SecurityRequirement(name = "developers:write"))
    @Loggable
    @PostMapping("/notify")
    @PreAuthorize("hasAuthority('developers:write')")
    ResponseEntity<Object> sendNotifyAboutUpcomingFilms(@RequestBody @Valid EmailNotifyDTO emailNotifyDTO) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c= Calendar.getInstance(); c.setTime(new Date());
        c.add(Calendar.DATE, emailNotifyDTO.getUpcomingInDays());
        Date lastDate = c.getTime();

        User user = userService.getByEmail(emailNotifyDTO.getEmail());
        if(user == null)
            throw new UsernameNotFoundException("Not existing email");

        StringBuilder msg = new StringBuilder("Hello " + user.getUsername() + ", want to remind you about some upcoming films:\n");
        filmService.getAllFilms().stream().filter(f -> f.getRelease().after(new Date()) &&
                 f.getRelease().before(lastDate))
                .forEach(film ->
                        msg.append(film.getTitle())
                        .append(" (release: ")
                        .append(dateFormat.format(film.getRelease()))
                        .append(", director: ")
                        .append(film.getDirector())
                        .append(")\n"));


        emailService.sendMessage(emailNotifyDTO.getEmail(), emailNotifyDTO.getTopic(), msg.toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
