package com.pedro.orso.outsera.web.rest;

import com.pedro.orso.outsera.service.StudioService;
import com.pedro.orso.outsera.web.rest.dto.StudioDto;
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


@Tag(name = "Studio", description = "Studio Resource")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/studios")
@RequiredArgsConstructor
@Validated
public class StudioResource {

    private final StudioService studioService;

    /**
     * {@code POST  /v1/studios} : Create a new studio.
     *
     * @param studioDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new Studio.
     */
    @Operation(summary = "Create a new studio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a studio"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<StudioDto> createStudio(@Valid @RequestBody StudioDto studioDto) {
        log.info("Request to create studio: {}", studioDto);

        StudioDto result = studioService.createStudio(studioDto);
        URI location = URI.create(String.format("/v1/studios/%s", result.getId()));

        log.info("Studio created successfully with id: {}", result.getId());

        return ResponseEntity.created(location).body(result);
    }

    /**
     * {@code GET  /v1/studios/:id} : Get the studio by id.
     *
     * @param id the id of the studio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} or {@code 404 (Not Found)}.
     */
    @Operation(summary = "Retrieve a studio by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Studio found"),
            @ApiResponse(responseCode = "404", description = "Studio not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudioDto> getStudio(@PathVariable Long id) {
        log.debug("REST request to get studio: {}", id);

        StudioDto studioDto = studioService.getStudioById(id);
        if (studioDto == null) {
            log.warn("Studio with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studioDto);
    }

    /**
     * {@code GET  /v1/studios} : Get all studios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studios in body.
     */
    @Operation(summary = "Returns a list of studios")
    @GetMapping
    public ResponseEntity<List<StudioDto>> getStudios(
            @PageableDefault(size = 20)
            @SortDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("REST request to get all studios");

        Page<StudioDto> result = studioService.getAllStudios(pageable);

        return ResponseEntity.ok().body(result.getContent());
    }

    /**
     * {@code PUT  /v1/studios/:id} : Updates an existing studio.
     *
     * @param id        the id of the studio to update.
     * @param studioDto the StudioDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studioDto,
     * or with status {@code 400 (Bad Request)} if the studioDto is not valid,
     * or with status {@code 404 (Not Found)} if the studio is not found.
     */
    @Operation(summary = "Updates a studio by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Studio updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input, object invalid"),
            @ApiResponse(responseCode = "404", description = "Studio not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudioDto> updateStudio(
            @PathVariable Long id,
            @Valid @RequestBody StudioDto studioDto
    ) {
        log.debug("REST request to update studio: {} {}", id, studioDto);

        StudioDto updatedStudioDto = studioService.updateStudio(id, studioDto);
        if (updatedStudioDto == null) {
            log.warn("Studio with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudioDto);
    }

    /**
     * {@code DELETE  /v1/studios/:id} : Delete the studio by id.
     *
     * @param id the id of the studio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)} or {@code 404 (NOT_FOUND)}.
     */
    @Operation(summary = "Deletes a studio by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Studio deleted"),
            @ApiResponse(responseCode = "404", description = "Studio not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        log.debug("REST request to delete studio: {}", id);

        boolean deleted = studioService.deleteStudioById(id);
        if (!deleted) {
            log.warn("Studio with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}