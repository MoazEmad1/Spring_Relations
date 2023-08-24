package com.example.relations.mapper;
import com.example.relations.dto.ActorDto;
import com.example.relations.dto.CityDto;
import com.example.relations.dto.MovieDto;
import com.example.relations.entity.City;
import com.example.relations.entity.Actor;
import com.example.relations.entity.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityDtoMapper {
    private static final Map<Object, Object> mappedObjects = new HashMap<>();

    public static CityDto mapCityToDto(City city) {

        if (mappedObjects.containsKey(city)) {
            return (CityDto) mappedObjects.get(city);
        }

        CityDto cityDto = new CityDto();
        cityDto.setId(city.getId());
        cityDto.setName(city.getName());

        mappedObjects.put(city, cityDto);
        if(city.getActors() != null) {
            List<ActorDto> actorsDto = city.getActors().stream()
                    .map(EntityDtoMapper::mapActorToDto)
                    .collect(Collectors.toList());
            cityDto.setActorsDto(actorsDto);
        }
        return cityDto;
    }

    public static ActorDto mapActorToDto(Actor actor) {
        if (mappedObjects.containsKey(actor)) {
            return (ActorDto) mappedObjects.get(actor);
        }
        ActorDto actorDto = new ActorDto();
        actorDto.setId(actor.getId());
        actorDto.setName(actor.getName());
        actorDto.setAge(actor.getAge());
        if(actor.getCity() != null) {
            actorDto.setCityDto(mapCityToDto(actor.getCity()));
        }
        if(actor.getMovies() != null) {
            actorDto.setMoviesDto(actor.getMovies().stream()
                    .map(EntityDtoMapper::mapMovieToDto)
                    .collect(Collectors.toList()));
        }

        mappedObjects.put(actor, actorDto);
        return actorDto;
    }

    public static MovieDto mapMovieToDto(Movie movie) {
        if (mappedObjects.containsKey(movie)) {
            return (MovieDto) mappedObjects.get(movie);
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        mappedObjects.put(movie, movieDto);
        if(movie.getActors() != null) {
            List<ActorDto> actorsDto = movie.getActors().stream()
                    .map(EntityDtoMapper::mapActorToDto)
                    .collect(Collectors.toList());
            movieDto.setActorsDto(actorsDto);
        }
        return movieDto;
    }

    public static City mapDtoToCity(CityDto cityDto) {
        if (mappedObjects.containsKey(cityDto)) {
            return (City) mappedObjects.get(cityDto);
        }
        City city = new City();
        city.setId(cityDto.getId());
        city.setName(cityDto.getName());
        if(cityDto.getActorsDto()!=null) {
            List<Actor> actors = cityDto.getActorsDto().stream()
                    .map(EntityDtoMapper::mapDtoToActor)
                    .collect(Collectors.toList());
            city.setActors(actors);
        }
        mappedObjects.put(cityDto, city);

        return city;
    }

    public static Actor mapDtoToActor(ActorDto actorDto) {
        if (mappedObjects.containsKey(actorDto)) {
            return (Actor) mappedObjects.get(actorDto);
        }
        Actor actor = new Actor();
        actor.setId(actorDto.getId());
        actor.setName(actorDto.getName());
        actor.setAge(actorDto.getAge());
        if(actorDto.getCityDto() != null) {
            actor.setCity(mapDtoToCity(actorDto.getCityDto()));
        }
        if(actorDto.getMoviesDto() != null) {
            actor.setMovies(actorDto.getMoviesDto().stream()
                    .map(EntityDtoMapper::mapDtoToMovie)
                    .collect(Collectors.toList()));
        }
        mappedObjects.put(actorDto, actor);
        return actor;
    }

    public static Movie mapDtoToMovie(MovieDto movieDto) {
        if (mappedObjects.containsKey(movieDto)) {
            return (Movie) mappedObjects.get(movieDto);
        }
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setTitle(movieDto.getTitle());
        mappedObjects.put(movieDto, movie);
        if(movieDto.getActorsDto() != null) {
            List<Actor> actors = movieDto.getActorsDto().stream()
                    .map(EntityDtoMapper::mapDtoToActor)
                    .collect(Collectors.toList());
            movie.setActors(actors);
        }
        return movie;
    }
}
