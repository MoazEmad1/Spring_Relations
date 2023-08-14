package com.example.relations.service;

import com.example.relations.repository.CityRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
}
