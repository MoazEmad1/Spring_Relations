package com.example.relations.service;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final ActorRepository actorRepository;
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(int id) {
        return cityRepository.findById(id).orElse(null);
    }

    public void saveCity(City city) {
        cityRepository.save(city);
    }

//    public void deleteCityById(int id) {
//        cityRepository.deleteById(id);
//    }
}





