package com.example.lab1.controller;

import com.example.lab1.forms.FilmForm;
import com.example.lab1.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FilmController
{
    private static List<Film> Films= new ArrayList<>();

    static {
        Films.add(new Film("Kill Bil vol.1", "Tarantino"));
        Films.add(new Film("Fight club", "Fincher"));
        log.info("-----------Add first data in list");
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("-----------add first data");

        return modelAndView;
    }

    @GetMapping("/filmlist")
    public ModelAndView playlist(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        model.addAttribute("films", Films);
        log.info("-----------filmlist");

        return modelAndView;
    }

    @GetMapping("/addfilm")
    public ModelAndView showAddFilmPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addFilm");
        FilmForm FilmForm = new FilmForm();
        model.addAttribute("filmform", FilmForm);
        log.info("-----------Show Add Film Page");

        return modelAndView;
    }

    @PostMapping("/addfilm")
    public ModelAndView addFilm(Model model, @ModelAttribute("filmform") FilmForm FilmForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        String title = FilmForm.getTitle();
        String author = FilmForm.getAuthor();
        if (title != null && !title.isEmpty() && author != null && !author.isEmpty()) {
            Film newFilm = new Film(title, author);
            Films.add(newFilm);
            log.info("-----------Add Film");
            model.addAttribute("films", Films);

            return new ModelAndView("redirect:/filmlist");
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addfilm");

        return modelAndView;
    }

    @GetMapping("/deletefilm")
    public ModelAndView showDeleteFilmPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("deleteFilm");
        FilmForm FilmForm = new FilmForm();
        model.addAttribute("filmform", FilmForm);
        log.info("-----------Show Add Film Page");

        return modelAndView;
    }


    @PostMapping("/deletefilm")
    public ModelAndView deleteFilm(Model model, @ModelAttribute("filmform") FilmForm FilmForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        String title = FilmForm.getTitle();
        if (title != null && !title.isEmpty()) {

            Films.removeIf(f -> f.getTitle().equals(title));
            log.info("-----------delete Film");

            return new ModelAndView("redirect:/filmlist");
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deletefilm");

        return modelAndView;
    }

    @PutMapping({"/edit"})
    public ModelAndView editFilm(String beforeChanges, String changes) {
        for (Film Film: Films) {
            if (Film.getAuthor().equals(beforeChanges)) {
                Film.setAuthor(changes);
                log.info("--------------Edit Film");
            }
            if (Film.getTitle().equals(beforeChanges)) {
                Film.setTitle(changes);
                log.info("--------------Edit Film");
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");

        return modelAndView;
    }
}