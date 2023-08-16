package com.example.relations.service;

import com.example.relations.dto.MovieDto;
import com.example.relations.entity.Actor;
import com.example.relations.entity.Movie;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.MovieRepository;
import com.example.relations.mapper.EntityDtoMapper; // Import the mapper class
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(EntityDtoMapper::mapMovieToDto)
                .collect(Collectors.toList());
    }
    public MovieDto getMovieById(int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null) {
            return EntityDtoMapper.mapMovieToDto(movie);
        }
        return null;
    }

    public void saveMovie(MovieDto movieDto, List<Integer> selectedActors) {
        Movie movie = EntityDtoMapper.mapDtoToMovie(movieDto);

        if (movie.getId() != null) {
            Movie existingMovie = movieRepository.findById(movie.getId()).orElse(null);
            if (existingMovie != null) {
                existingMovie.setTitle(movie.getTitle());
                existingMovie.getActors().clear();
                if (selectedActors != null) {
                    List<Actor> selectedActorList = actorRepository.findAllById(selectedActors);
                    for (Actor actor : selectedActorList) {
                        existingMovie.getActors().add(actor);
                        actor.getMovies().add(existingMovie);
                    }
                }
                movieRepository.save(existingMovie);
            }
        } else {
            if (selectedActors != null) {
                List<Actor> selectedActorList = actorRepository.findAllById(selectedActors);
                for (Actor actor : selectedActorList) {
                    movie.getActors().add(actor);
                    actor.getMovies().add(movie);
                }
            }
            movieRepository.save(movie);
        }
    }

    public void deleteMovieById(int id) {
        movieRepository.deleteById(id);
    }
}
