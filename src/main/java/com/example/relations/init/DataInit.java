package com.example.relations.init;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
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
    private final ActorService actorService;
    private final MovieService movieService;
    private final CityService cityService;


    //just adding data for the first run

    /*@PostConstruct
    public void init() {
        for (int i = 1; i <= 5; i++) {
            Actor actor = new Actor();
            actor.setName("Actor " + i);
            actor.setAge(20 + i % 10);
            Movie movie = new Movie();
            movie.setTitle("Movie " + i);

            City city = new City();
            city.setName("City " + i);

            cityService.getCityRepository().save(city);

            List<Actor> actorsList = new ArrayList<>();
            actorsList.add(actor);

            movie.setActors(actorsList);
            city.setActors(actorsList);

            actor.getMovies().add(movie);
            actor.setCity(city);

            actorService.getActorRepository().save(actor);
            movieService.getMovieRepository().save(movie);
        }
        System.out.println("Initial data added to the database.");
    }*/
}

