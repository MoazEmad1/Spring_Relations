package com.example.relations.service;

import com.example.relations.repository.ActorRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class ActorService {
    private final ActorRepository actorRepository;

}
