package com.pedro.orso.outsera.web.rest;


import com.pedro.orso.outsera.web.rest.dto.MovieDTO;
import com.pedro.orso.outsera.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@Tag(name = "Movie", description = "Movie Resource")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/movies")
@RequiredArgsConstructor
@Validated
public class MovieResource {

    private final MovieService movieService;

    /**
     * {@code POST  /v1/movies} : Create a new movie.
     *
     * @param movieDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Movie.
     */
    @Operation(summary = "Create a new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a movie"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDto) {
        log.info("Request to create movie: {}", movieDto);

        MovieDTO result = movieService.createMovie(movieDto);
        URI location = URI.create(String.format("/v1/movies/%s", result.getId()));

        log.info("Movie created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/movies/:id} : Get the movie by id.
     *
     * @param id the id of the movie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or {@code 404 (Not Found)}.
     */
    @Operation(summary = "Retrieve a movie by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        log.debug("REST request to get movie: {}", id);

        MovieDTO movieDto = movieService.getMovieById(id);
        if (movieDto == null) {
            log.warn("Movie with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDto);
    }

    /**
     * {@code GET  /v1/movies} : Get all movies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @Operation(summary = "Returns a list of movies")
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getMovies(
            @PageableDefault(size = 20)
            @SortDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("REST request to get all movies");

        Page<MovieDTO> result = movieService.getAllMovies(pageable);

        return ResponseEntity.ok().body(result.getContent());
    }

    /**
     * {@code PUT  /v1/movies/:id} : Updates an existing movie.
     *
     * @param id       the id of the movie to update.
     * @param movieDto the MovieDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movieDto,
     * or with status {@code 400 (Bad Request)} if the movieDto is not valid,
     * or with status {@code 404 (Not Found)} if the movie is not found.
     */
    @Operation(summary = "Updates a movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieDTO movieDto
    ) {
        log.debug("REST request to update movie: {} {}", id, movieDto);

        MovieDTO updatedMovieDto = movieService.updateMovie(id, movieDto);
        if (updatedMovieDto == null) {
            log.warn("Movie with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMovieDto);
    }

    /**
     * {@code DELETE  /v1/movies/:id} : Delete the movie by id.
     *
     * @param id the id of the movie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)} or {@code 404 (NOT_FOUND)}.
     */
    @Operation(summary = "Deletes a movie by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete movie: {}", id);

        boolean deleted = movieService.deleteMovieById(id);
        if (!deleted) {
            log.warn("Movie with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}