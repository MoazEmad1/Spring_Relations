package com.example.relations.controller;

import com.example.relations.entity.City;
import com.example.relations.entity.Actor;
import com.example.relations.service.CityService;
import com.example.relations.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    private final ActorService actorService;

    @GetMapping("/cities")
    public String showCities(Model model) {
        List<City> cities = cityService.getCityRepository().findAll();
        model.addAttribute("cities", cities);
        return "cities";
    }

    @GetMapping("/cities/add")
    public String showAddCityForm(Model model) {
        model.addAttribute("city", new City());
        model.addAttribute("actors", actorService.getActorRepository().findAll());

        return "city-form";
    }

    @PostMapping("/cities/save")
    public String saveCity(@ModelAttribute City city,
                           @RequestParam(name = "selectedActors", required = false) List<Integer> selectedActors) {
        cityService.getCityRepository().save(city);

        if (selectedActors != null) {
            List<Actor> selectedActorList = actorService.getActorRepository().findAllById(selectedActors);
            city.setActors(selectedActorList);

            for (Actor actor : selectedActorList) {
                actor.setCity(city);
                actorService.getActorRepository().save(actor);
            }
        }
        return "redirect:/cities";
    }

    @GetMapping("/cities/edit/{id}")
    public String showEditCityForm(@PathVariable int id, Model model) {
        City city = cityService.getCityRepository().findById(id).orElse(null);
        model.addAttribute("city", city);
        model.addAttribute("actors", actorService.getActorRepository().findAll());
        return "city-form";
    }

    @GetMapping("/cities/delete/{id}")
    public String deleteCity(@PathVariable int id) {
        cityService.getCityRepository().deleteById(id);
        return "redirect:/cities";
    }
}
