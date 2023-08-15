package com.example.relations.controller;

import com.example.relations.entity.Movie;
import com.example.relations.service.MovieService;
import com.example.relations.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final ActorService actorService;

    @GetMapping
    public String showAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("actors", actorService.getAllActors());
        return "movie-form";
    }

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie,
                            @RequestParam(name = "selectedActors", required = false) List<Integer> selectedActors) {
        movieService.saveMovie(movie, selectedActors);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable int id, Model model) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("actors", actorService.getAllActors());
        return "movie-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable int id) {
        movieService.deleteMovieById(id);
        return "redirect:/movies";
    }
}
