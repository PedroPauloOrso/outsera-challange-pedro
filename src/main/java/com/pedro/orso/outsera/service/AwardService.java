package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.web.rest.dto.ProducerIntervalDTO;
import com.pedro.orso.outsera.web.rest.dto.ProducerIntervalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final ProducerRepository producerRepository;

    public ProducerIntervalResponseDTO getProducersWithMinAndMaxInterval() {
        List<Object[]> results = producerRepository.getProducersWithMinAndMaxIntervals();

        List<ProducerIntervalDTO> minIntervals = new ArrayList<>();
        List<ProducerIntervalDTO> maxIntervals = new ArrayList<>();

        if (results.isEmpty()) {
            ProducerIntervalResponseDTO producerIntervalResponseDTO = new ProducerIntervalResponseDTO();
            producerIntervalResponseDTO.setMin(Collections.emptyList());
            producerIntervalResponseDTO.setMax(Collections.emptyList());
        }

        Integer minInterval = null;
        Integer maxInterval = null;

        for (Object[] row : results) {
            String producerName = (String) row[0];
            Integer interval = (Integer) row[1];
            Integer previousWin = (Integer) row[2];
            Integer followingWin = (Integer) row[3];

            ProducerIntervalDTO dto = new ProducerIntervalDTO();
            dto.setProducer(producerName);
            dto.setInterval(interval);
            dto.setPreviousWin(previousWin);
            dto.setFollowingWin(followingWin);

            if (minInterval == null || interval < minInterval) {
                minInterval = interval;
                minIntervals.clear();
                minIntervals.add(dto);
            } else if (interval.equals(minInterval)) {
                minIntervals.add(dto);
            }

            if (maxInterval == null || interval > maxInterval) {
                maxInterval = interval;
                maxIntervals.clear();
                maxIntervals.add(dto);
            } else if (interval.equals(maxInterval)) {
                maxIntervals.add(dto);
            }
        }
        ProducerIntervalResponseDTO producerIntervalResponseDTO = new ProducerIntervalResponseDTO();
        producerIntervalResponseDTO.setMax(maxIntervals);
        producerIntervalResponseDTO.setMin(minIntervals);

        return producerIntervalResponseDTO;
    }
}