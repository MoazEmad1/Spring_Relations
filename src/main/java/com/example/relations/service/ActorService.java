package com.example.relations.service;

import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.exception.UpdateFailException;
import com.example.relations.mapper.EntityDtoMapper;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ActorDto saveActor(ActorDto actorDto, List<Integer> selectedMovies) {
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

    public ActorDto updateActor(ActorDto actorDto, List<Integer> selectedMovies) {
        Actor existingActor = actorRepository.findById(actorDto.getId()).orElse(null);
        if (existingActor != null) {
            existingActor.setName(actorDto.getName());
            existingActor.setAge(actorDto.getAge());
            CityDto cityDto = actorDto.getCityDto();
            City city = EntityDtoMapper.mapDtoToCity(cityDto);
            existingActor.setCity(city);

            List<Movie> newMovies = new ArrayList<>();
            if (selectedMovies != null) {
                newMovies.addAll(movieRepository.findAllById(selectedMovies));
            }

            List<Movie> existingMovies = existingActor.getMovies();
            for (Movie movie : existingMovies) {
                if (!newMovies.contains(movie)) {
                    movie.getActors().remove(existingActor);
                }
            }

            existingMovies.retainAll(newMovies);
            for (Movie movie : newMovies) {
                if (!existingMovies.contains(movie)) {
                    existingMovies.add(movie);
                    movie.getActors().add(existingActor);
                }
            }

            Actor updatedActor = actorRepository.save(existingActor);
            return EntityDtoMapper.mapActorToDto(updatedActor);
        } else {
            throw new UpdateFailException("Actor not found with ID: " + actorDto.getId());
        }
    }


    public String deleteActorById(int actorId) {
        Optional<Actor> actor = actorRepository.findById(actorId);
        if (actor.isPresent()) {
            if(actor.get().getMovies()!=null) {
                actor.get().getMovies().forEach(movie -> movie.getActors().remove(actor.get()));
            }
            actorRepository.delete(actor.get());
            return "Actor deleted successfully";
        }
        else {
            return "Actor not found with ID: " + actorId;
        }
    }
}
