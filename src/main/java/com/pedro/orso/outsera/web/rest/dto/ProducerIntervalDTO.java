package com.pedro.orso.outsera.web.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ProducerIntervalDTO {

    private String producer;

    private int interval;

    private int previousWin;

    private int followingWin;


}
