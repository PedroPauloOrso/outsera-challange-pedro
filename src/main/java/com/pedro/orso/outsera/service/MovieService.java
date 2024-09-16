package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.repository.MovieRepository;
import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.web.rest.dto.MovieDTO;
import com.pedro.orso.outsera.web.rest.dto.ProducerDto;
import com.pedro.orso.outsera.web.rest.mapper.MovieMapper;
import com.pedro.orso.outsera.domain.Studio;
import com.pedro.orso.outsera.repository.StudioRepository;
import com.pedro.orso.outsera.web.rest.dto.StudioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final StudioRepository studioRepository;
    private final ProducerRepository producerRepository;

    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.toEntity(movieDTO);

        // Fetch and set studios
        if (movieDTO.getStudios() != null && !movieDTO.getStudios().isEmpty()) {
            List<Long> studioIds = movieDTO.getStudios().stream()
                    .map(StudioDto::getId)
                    .collect(Collectors.toList());

            List<Studio> studios = studioRepository.findAllById(studioIds);
            movie.setStudios(studios);
        } else {
            movie.setStudios(new ArrayList<>());
        }

        // Fetch and set producers
        if (movieDTO.getProducers() != null && !movieDTO.getProducers().isEmpty()) {
            List<Long> producerIds = movieDTO.getProducers().stream()
                    .map(ProducerDto::getId)
                    .collect(Collectors.toList());

            List<Producer> producers = producerRepository.findAllById(producerIds);
            movie.setProducers(producers);
        } else {
            movie.setProducers(new ArrayList<>());
        }

        movie = movieRepository.save(movie);
        log.info("Created new movie with id and title: {} {}", movie.getId(), movie.getTitle());

        return movieMapper.toDto(movie);
    }

    public MovieDTO getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElse(null);
    }

    public Page<MovieDTO> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(movieMapper::toDto);
    }

    public MovieDTO updateMovie(Long id, MovieDTO movieDto) {
        if (!movieRepository.existsById(id)) {
            return null;
        }
        Movie movie = movieMapper.toEntity(movieDto);
        movie.setId(id);
        movie = movieRepository.save(movie);
        log.info("Updated movie with id and title: {} {}", movie.getId(), movie.getTitle());

        return movieMapper.toDto(movie);
    }

    public boolean deleteMovieById(Long id) {
        if (!movieRepository.existsById(id)) {
            return false;
        }
        movieRepository.deleteById(id);
        log.info("Deleted movie with id: {}", id);

        return true;
    }
}