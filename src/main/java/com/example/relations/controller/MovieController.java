package com.example.relations.controller;

import com.example.relations.entity.Movie;
import com.example.relations.entity.Actor;
import com.example.relations.service.MovieService;
import com.example.relations.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final ActorService actorService;

    @GetMapping("/movies")
    public String showMovies(Model model) {
        List<Movie> movies = movieService.getMovieRepository().findAll();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/movies/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("actors", actorService.getActorRepository().findAll());

        return "movie-form";
    }

    @PostMapping("/movies/save")
    public String saveMovie(@ModelAttribute Movie movie,
                            @RequestParam(name = "selectedActors", required = false) List<Integer> selectedActors) {
        movieService.getMovieRepository().save(movie);

        if (selectedActors != null) {
            List<Actor> selectedActorList = actorService.getActorRepository().findAllById(selectedActors);
            movie.setActors(selectedActorList);

            for (Actor actor : selectedActorList) {
                actor.getMovies().add(movie);
                actorService.getActorRepository().save(actor);
            }
        }
        return "redirect:/movies";
    }

    @GetMapping("/movies/edit/{id}")
    public String showEditMovieForm(@PathVariable int id, Model model) {
        Movie movie = movieService.getMovieRepository().findById(id).orElse(null);
        model.addAttribute("movie", movie);
        model.addAttribute("actors", actorService.getActorRepository().findAll());
        return "movie-form";
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable int id) {
        movieService.getMovieRepository().deleteById(id);
        return "redirect:/movies";
    }
}
