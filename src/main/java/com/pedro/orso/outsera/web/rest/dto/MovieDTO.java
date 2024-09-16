package com.pedro.orso.outsera.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieDTO {

    private Long id;

    private String title;

    private int releaseYear;

    private Boolean winner;

    private List<StudioDto> studios;

    private List<ProducerDto> producers;

}
