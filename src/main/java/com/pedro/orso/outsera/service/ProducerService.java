package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.web.rest.dto.ProducerDto;
import com.pedro.orso.outsera.web.rest.mapper.ProducerMapper;
import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;

    public ProducerDto createProducer(ProducerDto producerDto) {
        Producer producer = producerMapper.toEntity(producerDto);
        producer = producerRepository.save(producer);
        return producerMapper.toDto(producer);
    }

    public ProducerDto getProducerById(Long id) {
        return producerRepository.findById(id)
                .map(producerMapper::toDto)
                .orElse(null);
    }

    public Page<ProducerDto> getAllProducers(Pageable pageable) {
        return producerRepository.findAll(pageable)
                .map(producerMapper::toDto);
    }

    public ProducerDto updateProducer(Long id, ProducerDto producerDto) {
        if (!producerRepository.existsById(id)) {
            return null;
        }
        Producer producer = producerMapper.toEntity(producerDto);
        producer.setId(id);
        producer = producerRepository.save(producer);
        return producerMapper.toDto(producer);
    }

    public boolean deleteProducerById(Long id) {
        if (!producerRepository.existsById(id)) {
            return false;
        }
        producerRepository.deleteById(id);
        return true;
    }
}