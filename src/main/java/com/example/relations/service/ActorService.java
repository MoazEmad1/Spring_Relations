package com.example.relations.service;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.CityRepository;
import com.example.relations.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final CityRepository cityRepository;

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor getActorById(int id) {
        return actorRepository.findById(id).orElse(null);
    }

    public void saveOrUpdateActor(Actor actor, List<Integer> selectedMovies) {
        Actor existingActor = actorRepository.findById(actor.getId()).orElse(null);

        if (existingActor != null) {
            existingActor.setName(actor.getName());
            existingActor.setAge(actor.getAge());
            existingActor.setCity(actor.getCity());

            for (Movie movie : existingActor.getMovies()) {
                movie.getActors().remove(existingActor);
            }
            existingActor.getMovies().clear();

            if (selectedMovies != null) {
                List<Movie> selectedMovieList = movieRepository.findAllById(selectedMovies);

                for (Movie movie : selectedMovieList) {
                    existingActor.getMovies().add(movie);
                    movie.getActors().add(existingActor);
                }
            }

            actorRepository.save(existingActor);
        }else {
            if (selectedMovies != null) {
                List<Movie> selectedMovieList = movieRepository.findAllById(selectedMovies);

                for (Movie movie : selectedMovieList) {
                    actor.getMovies().add(movie);
                    movie.getActors().add(actor);
                }
            }
            actorRepository.save(actor);

        }
    }


    public void deleteActorById(int actorId) {
        Actor actor = actorRepository.findById(actorId).orElse(null);
        if (actor != null) {
            for (Movie movie : actor.getMovies()) {
                movie.getActors().remove(actor);
            }
            City city = actor.getCity();
            if (city != null) {
                city.getActors().remove(actor);
                actor.setCity(null);
            }

            actorRepository.delete(actor);
        }
    }

}
