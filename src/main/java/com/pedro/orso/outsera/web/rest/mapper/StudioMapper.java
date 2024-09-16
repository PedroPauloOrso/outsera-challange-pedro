package com.pedro.orso.outsera.web.rest.mapper;

import com.pedro.orso.outsera.domain.Studio;
import com.pedro.orso.outsera.web.rest.dto.StudioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface StudioMapper extends EntityMapper<StudioDto, Studio> {

    StudioDto toDto(Studio studio);

    Studio toEntity(StudioDto studioDto);
}
