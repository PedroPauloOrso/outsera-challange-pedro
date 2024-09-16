package com.pedro.orso.outsera.web.rest.mapper;

import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.web.rest.dto.ProducerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducerMapper extends EntityMapper<ProducerDto, Producer> {

    ProducerDto toDto(Producer producer);

    Producer toEntity(ProducerDto producerDto);

}