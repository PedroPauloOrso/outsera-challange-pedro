package com.pedro.orso.outsera.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProducerIntervalResponseDTO {
    private List<ProducerIntervalDTO> min;
    private List<ProducerIntervalDTO> max;
}