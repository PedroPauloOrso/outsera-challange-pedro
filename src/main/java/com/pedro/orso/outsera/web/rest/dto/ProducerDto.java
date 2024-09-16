package com.pedro.orso.outsera.web.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProducerDto {

    private Long id;

    @NotBlank(message = "Producer name is required")
    private String name;

}
