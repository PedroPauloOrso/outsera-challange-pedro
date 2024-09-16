package com.pedro.orso.outsera.web.rest.mapper;

import com.pedro.orso.outsera.web.rest.dto.MovieDTO;
import com.pedro.orso.outsera.domain.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProducerMapper.class, StudioMapper.class})
public interface MovieMapper {

    @Mapping(target = "studios", source = "studios")
    @Mapping(target = "producers", source = "producers")
    MovieDTO toDto(Movie movie);

    @Mapping(target = "studios", source = "studios")
    @Mapping(target = "producers", source = "producers")
    Movie toEntity(MovieDTO movieDTO);
}