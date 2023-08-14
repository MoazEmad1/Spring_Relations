package com.example.relations.service;

import com.example.relations.repository.MovieRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
}
