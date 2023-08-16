package com.example.relations.service;

import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.dto.MovieDto;
import com.example.relations.entity.City;
import com.example.relations.entity.Actor;
import com.example.relations.entity.Movie;
import com.example.relations.repository.CityRepository;
import com.example.relations.mapper.EntityDtoMapper; // Import the mapper class
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityDto> getAllCities() {
        List<City> cities = cityRepository.findAll();
        return cities.stream()
                .map(EntityDtoMapper::mapCityToDto)
                .collect(Collectors.toList());
    }

    public CityDto getCityById(int id) {
        City city = cityRepository.findById(id).orElse(null);
        if (city != null) {
            return EntityDtoMapper.mapCityToDto(city);
        }
        return null;
    }

    public void saveCity(CityDto cityDto) {
        City city = EntityDtoMapper.mapDtoToCity(cityDto);
        cityRepository.save(city);
    }
}
