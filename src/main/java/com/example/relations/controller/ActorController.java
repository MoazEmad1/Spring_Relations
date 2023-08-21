package com.example.relations.controller;

import com.example.relations.dto.ActorDto;
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
        List<ActorDto> actorDtos = actorService.getAllActors();
        model.addAttribute("actors", actorDtos);
        return "actors";
    }

    @GetMapping("/add")
    public String showAddActorForm(Model model) {
        model.addAttribute("actor", new ActorDto());
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("movies", movieService.getAllMovies());
        return "actor-form";
    }

    @PostMapping("/save")
    public String saveOrUpdateActor(
            @ModelAttribute ActorDto actorDto,
            @RequestParam(name = "selectedMovies", required = false) List<Integer> selectedMovies) {
        if(actorDto.getId() == null)
            actorService.saveActor(actorDto, selectedMovies);
        else
            actorService.updateActor(actorDto, selectedMovies);
        return "redirect:/actors";
    }


    @GetMapping("/edit/{id}")
    public String showEditActorForm(@PathVariable int id, Model model) {
        ActorDto actorDto = actorService.getActorById(id);
        model.addAttribute("actor", actorDto);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("movies", movieService.getAllMovies());
        return "actor-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteActor(@PathVariable int id,Model model) {
        String deleteMessage = actorService.deleteActorById(id);
        if (deleteMessage.startsWith("Actor not found")) {
            model.addAttribute("errorMessage", deleteMessage);
            return "error-page";
        }
        return "redirect:/actors";
    }
}
