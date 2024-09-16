package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.domain.Studio;
import com.pedro.orso.outsera.repository.StudioRepository;
import com.pedro.orso.outsera.web.rest.dto.StudioDto;
import com.pedro.orso.outsera.web.rest.mapper.StudioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    public StudioDto createStudio(StudioDto studioDto) {
        Studio studio = studioMapper.toEntity(studioDto);
        studio = studioRepository.save(studio);
        return studioMapper.toDto(studio);
    }

    public StudioDto getStudioById(Long id) {
        return studioRepository.findById(id)
                .map(studioMapper::toDto)
                .orElse(null);
    }

    public Page<StudioDto> getAllStudios(Pageable pageable) {
        return studioRepository.findAll(pageable)
                .map(studioMapper::toDto);
    }

    public StudioDto updateStudio(Long id, StudioDto studioDto) {
        if (!studioRepository.existsById(id)) {
            return null;
        }
        Studio studio = studioMapper.toEntity(studioDto);
        studio.setId(id);
        studio = studioRepository.save(studio);
        return studioMapper.toDto(studio);
    }

    public boolean deleteStudioById(Long id) {
        if (!studioRepository.existsById(id)) {
            return false;
        }
        studioRepository.deleteById(id);
        return true;
    }
}