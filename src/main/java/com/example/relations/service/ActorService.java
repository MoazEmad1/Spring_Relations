package com.example.relations.service;

import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.mapper.EntityDtoMapper;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public List<ActorDto> getAllActors() {
        List<ActorDto> actorDtos = new ArrayList<>();
        List<Actor> actors = actorRepository.findAll();
        for (Actor actor : actors) {
            actorDtos.add(EntityDtoMapper.mapActorToDto(actor));
        }
        return actorDtos;
    }

    public ActorDto getActorById(int id) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor != null) {
            return EntityDtoMapper.mapActorToDto(actor);
        }
        return null;
    }

    public ActorDto saveOrUpdateActor(ActorDto actorDto, List<Integer> selectedMovies) {
        Actor existingActor = actorRepository.findById(actorDto.getId()).orElse(null);

        if (existingActor != null) {
            existingActor.setName(actorDto.getName());
            existingActor.setAge(actorDto.getAge());

            CityDto cityDto = actorDto.getCityDto();
            City city = EntityDtoMapper.mapDtoToCity(cityDto);
            existingActor.setCity(city);
            List<Movie> movies = existingActor.getMovies();
            for (Movie movie : movies) {
                movie.getActors().remove(existingActor);
            }
            movies.clear();
            if (selectedMovies != null) {
                List<Movie> selectedMovieList = movieRepository.findAllById(selectedMovies);
                for (Movie movie : selectedMovieList) {
                    movies.add(movie);
                    movie.getActors().add(existingActor);
                }
            }
            Actor savedActor = actorRepository.save(existingActor);

            return EntityDtoMapper.mapActorToDto(savedActor);
        } else {
            Actor newActor = EntityDtoMapper.mapDtoToActor(actorDto);

            CityDto cityDto = actorDto.getCityDto();
            newActor.setCity(EntityDtoMapper.mapDtoToCity(cityDto));
            if (selectedMovies != null) {
                List<Movie> selectedMovieList = movieRepository.findAllById(selectedMovies);
                for (Movie movie : selectedMovieList) {
                    newActor.getMovies().add(movie);
                    movie.getActors().add(newActor);
                }
            }
            Actor savedActor = actorRepository.save(newActor);
            return EntityDtoMapper.mapActorToDto(savedActor);
        }

    }


    public void deleteActorById(int actorId) {
        Actor actor = actorRepository.findById(actorId).orElse(null);
        if (actor != null) {
            List<Movie> movies = actor.getMovies();
            for (Movie movie : movies) {
                movie.getActors().remove(actor);
            }
            actorRepository.delete(actor);
        }
    }
}
