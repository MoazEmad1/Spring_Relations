package com.example.relations.init;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.CityRepository;
import com.example.relations.repository.MovieRepository;
import com.example.relations.service.ActorService;
import com.example.relations.service.CityService;
import com.example.relations.service.MovieService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final CityRepository cityRepository;
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;


    //just adding data for the first run

    /*@PostConstruct
    public void init() {
        for (int i = 0; i < 5; i++) {

            City city = new City();
            city.setName("City " + i);
            cityRepository.save(city);
            Actor actor = new Actor();
            actor.setName("Actor " + i);
            actor.setAge(20 + i % 10);
            actor.setCity(city);
            actorRepository.save(actor);
            Movie movie = new Movie();
            movie.setTitle("Movie " + i);
            List<Actor> actors = new ArrayList<>();
            actors.add(actor);
            movie.setActors(actors);
            movieRepository.save(movie);
        }
        System.out.println("Initial data added to the database.");
    }*/
}

