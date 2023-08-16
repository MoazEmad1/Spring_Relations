package com.example.relations.service;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.CityRepository;
import com.example.relations.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private ActorService actorService;

    @Test
    public void ActorService_CreateActor_ReturnActor() {
        // Arrange
        City city1 = City.builder().name("city1").build();
        cityRepository.save(city1);
        
        Movie movie1 = Movie.builder().title("movie1").actors(new ArrayList<>()).build();
        Movie movie2 = Movie.builder().title("movie2").actors(new ArrayList<>()).build();

        List<Movie> actorMovies = new ArrayList<>();
        actorMovies.add(movie1);
        actorMovies.add(movie2);

        List<Integer>  selectedMovies = new ArrayList<>();
        selectedMovies.add(movie1.getId());
        selectedMovies.add(movie2.getId());

        Actor actor = Actor.builder()
                .name("Actor1")
                .age(21)
                .city(city1)
                .movies(actorMovies)
                .build();

        movieRepository.save(movie1);
        movieRepository.save(movie2);


        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        when(movieRepository.findAllById(selectedMovies)).thenReturn(List.of(movie1, movie2));

        // Act
        actorService.saveOrUpdateActor(actor, selectedMovies);

        // Assert
        assertThat(actor.getMovies()).containsExactly(movie1, movie2);
        assertThat(movie1.getActors()).containsExactly(actor);
        assertThat(movie2.getActors()).containsExactly(actor);

        verify(actorRepository, times(1)).save(actor);
        verify(movieRepository, times(2)).save(any(Movie.class));
    }

    @Test
    @Disabled
    void deleteActorById() {
        Integer id=2;
        actorService.deleteActorById(id);
        given(actorRepository.existsById(id)).willReturn(false);
        assertThatThrownBy(()->actorService.getActorById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Actor with id "+id+" does not exist");
        verify(actorRepository,never()).deleteById(any());

    }
}