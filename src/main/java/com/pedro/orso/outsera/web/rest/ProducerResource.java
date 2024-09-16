package com.pedro.orso.outsera.web.rest;

import com.pedro.orso.outsera.service.ProducerService;
import com.pedro.orso.outsera.web.rest.dto.ProducerDto;
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

@Tag(name = "Producer", description = "Producer Resource")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class ProducerResource {

    private static final String PRODUCER_URI = "/v1/producers";

    private final ProducerService producerService;

    /**
     * {@code POST  /v1/producers} : Create a new producer.
     *
     * @param producerDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Producer.
     */
    @Operation(summary = "Create a new producer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a producer"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(PRODUCER_URI)
    public ResponseEntity<ProducerDto> createProducer(@Valid @RequestBody ProducerDto producerDto) {
        log.info("Request to create producer: {}", producerDto);

        ProducerDto result = producerService.createProducer(producerDto);
        URI location = URI.create(String.format("/v1/producers/%s", result.getId()));

        log.info("Producer created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/producers/:id} : Get the producer by id.
     *
     * @param id the id of the producer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or {@code 404 (Not Found)}.
     */
    @Operation(summary = "Retrieve a producer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producer found"),
            @ApiResponse(responseCode = "404", description = "Producer not found")
    })
    @GetMapping(PRODUCER_URI + "/{id}")
    public ResponseEntity<ProducerDto> getProducer(
            @PathVariable Long id
    ) {
        log.debug("REST request to get producer: {}", id);

        ProducerDto producerDto = producerService.getProducerById(id);
        if (producerDto == null) {
            log.warn("Producer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producerDto);
    }

    /**
     * {@code GET  /v1/producers} : Get all producers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of producers in body.
     */
    @Operation(summary = "Returns a list of producers")
    @GetMapping(PRODUCER_URI)
    public ResponseEntity<List<ProducerDto>> getProducers(
            @PageableDefault(size = 20)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("REST request to get all producers");

        Page<ProducerDto> result = producerService.getAllProducers(pageable);

        return ResponseEntity.ok().body(result.getContent());
    }

    /**
     * {@code PUT  /v1/producers/:id} : Updates an existing producer.
     *
     * @param id          the id of the producer to update.
     * @param producerDto the ProducerDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producerDto,
     * or with status {@code 400 (Bad Request)} if the producerDto is not valid,
     * or with status {@code 404 (Not Found)} if the producer is not found.
     */
    @Operation(summary = "Updates a producer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producer updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "404", description = "Producer not found")
    })
    @PutMapping(PRODUCER_URI + "/{id}")
    public ResponseEntity<ProducerDto> updateProducer(
            @PathVariable Long id,
            @Valid @RequestBody ProducerDto producerDto
    ) {
        log.debug("REST request to update producer: {} {}", id, producerDto);

        ProducerDto updatedProducerDto = producerService.updateProducer(id, producerDto);
        if (updatedProducerDto == null) {
            log.warn("Producer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProducerDto);
    }

    /**
     * {@code DELETE  /v1/producers/:id} : Delete the producer by id.
     *
     * @param id the id of the producer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)} or {@code 404 (NOT_FOUND)}.
     */
    @Operation(summary = "Deletes a producer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producer deleted"),
            @ApiResponse(responseCode = "404", description = "Producer not found")
    })
    @DeleteMapping(PRODUCER_URI + "/{id}")
    public ResponseEntity<Void> deleteProducer(
            @PathVariable Long id
    ) {
        log.debug("REST request to delete producer: {}", id);

        boolean deleted = producerService.deleteProducerById(id);
        if (!deleted) {
            log.warn("Producer with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}