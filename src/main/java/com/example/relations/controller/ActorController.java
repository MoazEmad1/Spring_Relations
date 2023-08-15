package com.example.relations.controller;

import com.example.relations.entity.Actor;
import com.example.relations.service.ActorService;
import com.example.relations.service.CityService;
import com.example.relations.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;
    private final CityService cityService;
    private final MovieService movieService;

    @GetMapping
    public String showAllActors(Model model) {
        List<Actor> actors = actorService.getAllActors();
        model.addAttribute("actors", actors);
        return "actors";
    }

    @GetMapping("/add")
    public String showAddActorForm(Model model) {
        model.addAttribute("actor", new Actor());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("movies", movieService.getAllMovies());
        return "actor-form";
    }

    @PostMapping("/save")
    public String saveOrUpdateActor(
            @ModelAttribute Actor actor,
            @RequestParam(name = "selectedMovies", required = false) List<Integer> selectedMovies) {
        actorService.saveOrUpdateActor(actor, selectedMovies);
        return "redirect:/actors";
    }

    @GetMapping("/edit/{id}")
    public String showEditActorForm(@PathVariable int id, Model model) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("actor", actor);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("movies", movieService.getAllMovies());
        return "actor-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteActor(@PathVariable int id) {
        actorService.deleteActorById(id);
        return "redirect:/actors";
    }
}
