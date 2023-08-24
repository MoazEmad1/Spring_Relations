package com.example.relations.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Integer id;
    private String title;
    private List<ActorDto> actorsDto=new ArrayList<>();
}
