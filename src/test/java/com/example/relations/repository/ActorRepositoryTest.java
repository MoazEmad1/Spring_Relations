package com.example.relations.repository;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ActorRepositoryTest {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Test
    void ActorRepository_FindByCity_ReturnActor(){
        //arrange
        City city1=City.builder().name("city1").build();
        cityRepository.save(city1);
        Actor actor = Actor.builder()
                .name("Actor1")
                .age(21)
                .city(city1)
                .movies(null).build();
        //act
        actorRepository.save(actor);
//        actorRepository.delete(actor);

        //assert
        assertThat(actorRepository.findByCity(city1)).isEqualTo(actor);
    }

}