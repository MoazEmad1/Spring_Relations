package com.example.relations.repository;

import com.example.relations.entity.Actor;
import com.example.relations.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findByCity(City city);
}
