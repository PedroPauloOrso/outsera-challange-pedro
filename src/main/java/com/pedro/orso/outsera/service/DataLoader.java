package com.pedro.orso.outsera.service;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.domain.Studio;
import com.pedro.orso.outsera.repository.MovieRepository;
import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.repository.StudioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("movielist.csv").getInputStream())) {
            CSVParserBuilder parserBuilder = new CSVParserBuilder();
            parserBuilder.withSeparator(';');

            CSVReaderBuilder readerBuilder = new CSVReaderBuilder(reader);
            readerBuilder.withCSVParser(parserBuilder.build());
            // Skipping the headers
            readerBuilder.withSkipLines(1);

            try (var csvReader = readerBuilder.build()) {
                String[] values;

                while ((values = csvReader.readNext()) != null) {
                    // Checking if it has all values
                    if (values.length < 5) {
                        continue;
                    }

                    String yearValue = values[0];
                    String titleValue = values[1];
                    String studiosValue = values[2];
                    String producersValue = values[3];
                    String winnerValue = values[4];

                    int year = Integer.parseInt(yearValue.trim());
                    boolean winner = "yes".equalsIgnoreCase(winnerValue.trim());

                    // Studios
                    List<Studio> studiosList = processStudios(studiosValue);

                    // Producers
                    List<Producer> producersList = processProducers(producersValue);

                    Movie movie = new Movie();
                    movie.setTitle(titleValue.trim());
                    movie.setReleaseYear(year);
                    movie.setStudios(studiosList);
                    movie.setProducers(producersList);
                    movie.setWinner(winner);

                    movieRepository.save(movie);
                    log.debug("Saved movie: {}", movie.getTitle());
                }
            }
        }
    }

    private List<Producer> processProducers(String producersValue) {
        List<Producer> producers = new ArrayList<>();
        String[] producerNames = producersValue.split(",| and ");
        for (String producerName : producerNames) {
            String trimmedName = producerName.trim();
            if (trimmedName.isEmpty()) {
                continue;
            }

            // Handle multiple producers separated by ' and ' as well
            String[] subProducerNames = trimmedName.split(" and ");
            for (String subProducerName : subProducerNames) {
                String finalName = subProducerName.trim();
                if (finalName.isEmpty()) {
                    continue;
                }

                Optional<Producer> optionalProducer = producerRepository.findByName(finalName);
                Producer producer;
                if (optionalProducer.isPresent()) {
                    producer = optionalProducer.get();
                    log.debug("Found existing producer: {}", producer.getName());
                } else {
                    Producer newProducer = new Producer();
                    newProducer.setName(finalName);
                    producer = producerRepository.save(newProducer);
                    log.debug("Created new Producer: {}", producer.getName());
                }
                producers.add(producer);
            }
        }
        return producers;
    }

    private List<Studio> processStudios(String studiosValue) {
        List<Studio> studios = new ArrayList<>();
        String[] studioNames = studiosValue.split(",");
        for (String studioName : studioNames) {
            String trimmedName = studioName.trim();
            if (trimmedName.isEmpty()) {
                continue;
            }

            Optional<Studio> optionalStudio = studioRepository.findByName(trimmedName);
            Studio studio;

            if (optionalStudio.isPresent()) {
                studio = optionalStudio.get();
                log.debug("Found existing studio: {}", studio.getName());
            } else {
                Studio newStudio = new Studio();
                newStudio.setName(trimmedName);
                studio = studioRepository.save(newStudio);
                log.debug("Created new studio: {}", studio.getName());
            }

            studios.add(studio);
        }
        return studios;
    }
}