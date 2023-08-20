package com.example.relations.service;

import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import com.example.relations.entity.Movie;
import com.example.relations.mapper.EntityDtoMapper;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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
    public void ActorService_CreateActor_ReturnActorDto() {
        int existingActorId = 1;
        CityDto cityDto = CityDto.builder().id(1).name("city1").build();
        ActorDto actorDto = ActorDto.builder().id(1).name("actor1").age(20).cityDto(cityDto).build();
        cityDto.setActorsDto(List.of(actorDto));

        actorDto.setId(existingActorId);
        List<Integer> selectedMovies = new ArrayList<>();
        selectedMovies.add(1);

        City city =City.builder().id(1).name("city1").build();
        Actor existingActor = Actor.builder().id(1).name("actor1").age(20).city(city).build();
        city.setActors(List.of(existingActor));

        existingActor.setId(existingActorId);

        when(actorRepository.findById(existingActorId)).thenReturn(Optional.of(existingActor));
        when(movieRepository.findAllById(selectedMovies)).thenReturn(new ArrayList<>());

        ActorDto updatedActorDto = actorService.saveOrUpdateActor(actorDto, selectedMovies);

        assertThat(updatedActorDto).isNotNull();
        assertThat(updatedActorDto.getId()).isEqualTo(existingActorId);
        assertThat(updatedActorDto.getName()).isEqualTo(actorDto.getName());
        assertThat(updatedActorDto.getAge()).isEqualTo(actorDto.getAge());

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