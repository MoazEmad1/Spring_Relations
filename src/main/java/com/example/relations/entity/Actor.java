package com.example.relations.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List; // Import the List interface

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "city_id")
    private City city;
}
