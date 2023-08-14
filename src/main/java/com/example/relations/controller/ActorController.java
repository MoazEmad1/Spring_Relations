package com.example.relations.controller;

import com.example.relations.entity.Actor;
import com.example.relations.entity.Movie;
import com.example.relations.service.ActorService;
import com.example.relations.service.CityService;
import com.example.relations.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;
    private final CityService cityService;
    private final MovieService movieService;


    @GetMapping("/actors")
    public String showActors(Model model) {
        List<Actor> actors = actorService.getActorRepository().findAll();
        model.addAttribute("actors", actors);
        return "actors";
    }

    @GetMapping("/actors/add")
    public String showAddActorForm(Model model) {
        model.addAttribute("actor", new Actor());
        model.addAttribute("cities", cityService.getCityRepository().findAll());
        model.addAttribute("movies", movieService.getMovieRepository().findAll());

        return "actor-form";
    }

    @PostMapping("/actors/save")
    public String updateActor(
            @ModelAttribute Actor actor,
            @RequestParam(name = "selectedMovies", required = false) List<Integer> selectedMovies) {
        actorService.getActorRepository().save(actor);

        if (selectedMovies != null) {
            List<Movie> selectedMovieList = movieService.getMovieRepository().findAllById(selectedMovies);
            actor.setMovies(selectedMovieList);

            for (Movie movie : selectedMovieList) {
                movie.getActors().add(actor);
                movieService.getMovieRepository().save(movie);
            }
        }
        return "redirect:/actors";
    }

    @GetMapping("/actors/edit/{id}")
    public String showEditActorForm(@PathVariable int id, Model model) {
        Actor actor = actorService.getActorRepository().findById(id).get();
        model.addAttribute("actor", actor);
        model.addAttribute("cities", cityService.getCityRepository().findAll());
        model.addAttribute("movies", movieService.getMovieRepository().findAll());
        return "actor-form";
    }

    @GetMapping("/actors/delete/{id}")
    public String deleteActor(@PathVariable int id) {
        actorService.getActorRepository().deleteById(id);
        return "redirect:/actors";
    }
}
