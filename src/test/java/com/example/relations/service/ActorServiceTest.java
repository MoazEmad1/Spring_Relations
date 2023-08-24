package com.example.relations.service;

import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.dto.MovieDto;
import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.mapper.EntityDtoMapper;
import com.example.relations.repository.ActorRepository;
import com.example.relations.repository.MovieRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {
    @Mock
    private ActorRepository actorRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private ActorService actorService;

    @Test
    public void ActorService_GetAllActor() {
        List<Actor> mockActors = new ArrayList<>();
        mockActors.add(Actor.builder().name("Actor1").age(20).build());
        when(actorRepository.findAll()).thenReturn(mockActors);

        List<ActorDto> actorDtos = actorService.getAllActors();

        assertThat( actorDtos.size()).isEqualTo(mockActors.size());
    }

    @Test
    public void ActorService_GetActorById() {
        int actorId = 1;
        Actor actor = new Actor();
        when(actorRepository.findById(actorId)).thenReturn(Optional.of(actor));
        ActorDto actorDto = actorService.getActorById(actorId);
        assertThat(actorDto).isNotNull();
    }
    @Test
    public void ActorService_SaveActor() {
        ActorDto actorDto = new ActorDto();
        actorDto.setName("Moaz");
        actorDto.setAge(20);

        CityDto cityDto = new CityDto();
        cityDto.setId(1);
        cityDto.setName("city1");
        actorDto.setCityDto(cityDto);

        List<Integer> selectedMovies = new ArrayList<>();
        selectedMovies.add(1);
        selectedMovies.add(2);

        Actor savedActor =Actor.builder().id(1).name("Moaz").age(20).build();

        when(actorRepository.save(any())).thenReturn(savedActor);
        when(movieRepository.findAllById(anyList())).thenReturn(new ArrayList<>());
        ActorDto result = actorService.saveActor(actorDto, selectedMovies);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Moaz");
        assertThat(result.getAge()).isEqualTo(20);
        assertThat(result.getMoviesDto().size()).isEqualTo(0);
    }




    @Test
//    @Disabled
    void deleteActorById() {
        Actor actor = Actor.builder().id(1).name("Moaz").age(20).build();

        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));

        String result = actorService.deleteActorById(1);

        verify(actorRepository, times(1)).findById(1);
        verify(actorRepository, times(1)).delete(actor);
        assertThat(result).isEqualTo("Actor deleted successfully");
    }

}