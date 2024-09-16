package com.pedro.orso.outsera.web.rest;

import com.pedro.orso.outsera.web.rest.dto.ProducerIntervalResponseDTO;
import com.pedro.orso.outsera.service.AwardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Award", description = "Awards Resource")
@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class AwardResource {

    private static final String AWARDS_URI = "/v1/awards";

    private final AwardService awardService;

    /**
     * {@code GET  /v1/awards} : Obtem o produtor com maior intervalo entre dois prêmios consecutivos, e o que
     * obteve dois prêmios mais rápido.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)},
     * or with status {@code 404 (Not Found)}.
     */
    @ApiOperation(value = "Obtem o produtor com maior intervalo entre dois prêmios consecutivos, e o que obteve dois prêmios mais rápido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "award found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProducerIntervalResponseDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)
    })
    @GetMapping(AWARDS_URI + "/intervals")
    public ResponseEntity<ProducerIntervalResponseDTO> getProducersWithMinAndMaxInterval() {
        ProducerIntervalResponseDTO response = awardService.getProducersWithMinAndMaxInterval();
        return ResponseEntity.ok(response);
    }


}
