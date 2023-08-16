package com.example.relations.service;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.CityRepository;
import com.example.relations.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private MovieService movieService;

    /*@Test
    void MovieService_SaveMovie_ReturnsMovie() {
        City city1 = City.builder().name("city1").build();

        when(cityRepository.save(any(City.class))).thenReturn(city1);

        Actor actor = Actor.builder()
                .name("Actor1")
                .age(21)
                .city(city1)
                .movies(new ArrayList<>())
                .build();
        Actor actor1 = Actor.builder()
                .name("Actor2")
                .age(22)
                .city(city1)
                .movies(new ArrayList<>())
                .build();

        List<Actor> actorList = new ArrayList<>();
        actorList.add(actor);
        actorList.add(actor1);

        Movie movie = Movie.builder()
                .title("Movie1")
                .actors(actorList)
                .build();
        actor.getMovies().add(movie);
        actor1.getMovies().add(movie);

        when(actorRepository.save(any(Actor.class))).thenReturn(actor).thenReturn(actor1);
        when(actorRepository.findAllById(anyList())).thenReturn(actorList);

        when(movieRepository.findById(movie.getId())).thenReturn(Optional.of(movie));
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        when(actorRepository.findById(actor1.getId())).thenReturn(Optional.of(actor1));

        movieService.saveMovie(movie, actorList.stream().map(Actor::getId).collect(Collectors.toList()));

        movieRepository.save(movie);

        assertThat(movie.getActors()).containsExactly(actor, actor1);
        assertThat(actor.getMovies()).containsExactly(movie);
        assertThat(actor1.getMovies()).containsExactly(movie);

        verify(cityRepository, times(1)).save(any(City.class));
        verify(actorRepository, times(2)).save(any(Actor.class));
        verify(movieRepository, times(1)).findById(movie.getId());
        verify(actorRepository, times(1)).findAllById(actorList.stream().map(Actor::getId).collect(Collectors.toList()));
    }*/
}
