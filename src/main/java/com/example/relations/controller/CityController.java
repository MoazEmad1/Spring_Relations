package com.example.relations.controller;

import com.example.relations.dto.CityDto;
import com.example.relations.service.CityService;
import com.example.relations.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    private final ActorService actorService;

    @GetMapping
    public String showAllCities(Model model) {
        List<CityDto> cityDtos = cityService.getAllCities();
        model.addAttribute("cities", cityDtos);
        return "cities";
    }

    @GetMapping("/add")
    public String showAddCityForm(Model model) {
        model.addAttribute("city", new CityDto());
        model.addAttribute("actors", actorService.getAllActors());
        return "city-form";
    }

    @PostMapping("/save")
    public String saveCity(@ModelAttribute CityDto cityDto) {
        cityService.saveCity(cityDto);
        return "redirect:/cities";
    }

    @GetMapping("/edit/{id}")
    public String showEditCityForm(@PathVariable int id, Model model) {
        CityDto cityDto = cityService.getCityById(id);
        model.addAttribute("city", cityDto);
        model.addAttribute("actors", actorService.getAllActors());
        return "city-form";
    }

/*    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable int id) {
        cityService.deleteCityById(id);
        return "redirect:/cities";
    }*/
}
