package com.example.relations.service;

import com.example.relations.entity.Movie;
import com.example.relations.entity.Actor;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    public void saveMovie(Movie movie, List<Integer> selectedActors) {
        Movie existingMovie = movieRepository.findById(movie.getId()).orElse(null);

        if (existingMovie != null) {
            existingMovie.setTitle(movie.getTitle());

            for (Actor actor : existingMovie.getActors()) {
                actor.getMovies().remove(existingMovie);
            }
            existingMovie.getActors().clear();

            if (selectedActors != null) {
                List<Actor> selectedActorList = actorRepository.findAllById(selectedActors);

                for (Actor actor : selectedActorList) {
                    existingMovie.getActors().add(actor);
                    actor.getMovies().add(existingMovie);
                }
            }

            movieRepository.save(existingMovie);
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