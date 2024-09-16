package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.web.rest.dto.ProducerIntervalDTO;
import com.pedro.orso.outsera.web.rest.dto.ProducerIntervalResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AwardService {

    private final ProducerRepository producerRepository;

    public ProducerIntervalResponseDTO getProducersWithMinAndMaxInterval() {
        List<Producer> producers = producerRepository.findAll();

        List<ProducerIntervalDTO> intervals = new ArrayList<>();

        for (Producer producer : producers) {
            List<Integer> winningYears = producer.getMovies().stream()
                    .filter(Movie::isWinner)
                    .map(Movie::getReleaseYear)
                    .sorted()
                    .collect(Collectors.toList());

            if (winningYears.size() >= 2) {
                for (int i = 1; i < winningYears.size(); i++) {
                    int interval = winningYears.get(i) - winningYears.get(i - 1);

                    ProducerIntervalDTO producerIntervalDTO = new ProducerIntervalDTO();
                    producerIntervalDTO.setProducer(producer.getName());
                    producerIntervalDTO.setInterval(interval);
                    producerIntervalDTO.setPreviousWin(winningYears.get(i - 1));
                    producerIntervalDTO.setFollowingWin(winningYears.get(i));

                    intervals.add(producerIntervalDTO);
                }
            }
        }

        if (intervals.isEmpty()) {
            ProducerIntervalResponseDTO producerIntervalResponseDTO = new ProducerIntervalResponseDTO();
            producerIntervalResponseDTO.setMax(Collections.emptyList());
            producerIntervalResponseDTO.setMin(Collections.emptyList());

            return producerIntervalResponseDTO;
        }

        int minInterval = intervals.stream()
                .mapToInt(ProducerIntervalDTO::getInterval)
                .min()
                .orElse(0);

        int maxInterval = intervals.stream()
                .mapToInt(ProducerIntervalDTO::getInterval)
                .max()
                .orElse(0);

        List<ProducerIntervalDTO> minIntervals = intervals.stream()
                .filter(pi -> pi.getInterval() == minInterval)
                .collect(Collectors.toList());

        List<ProducerIntervalDTO> maxIntervals = intervals.stream()
                .filter(pi -> pi.getInterval() == maxInterval)
                .collect(Collectors.toList());

        ProducerIntervalResponseDTO producerIntervalResponseDTO = new ProducerIntervalResponseDTO();
        producerIntervalResponseDTO.setMax(maxIntervals);
        producerIntervalResponseDTO.setMin(minIntervals);

        return producerIntervalResponseDTO;
    }
}