package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.repository.MovieRepository;
import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.repository.StudioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class DataLoaderIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @DynamicPropertySource
    static void dataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () ->
                "jdbc:h2:mem:data_loader_test_db;DB_CLOSE_DELAY=-1");
    }

    @Test
    @Transactional
    public void testDataLoaderLoadsData() {
        // Check that movies have been loaded
        long movieCount = movieRepository.count();
        long producerCount = producerRepository.count();
        long studioCount = studioRepository.count();

        System.out.println("Movies loaded: " + movieCount);
        System.out.println("Producers loaded: " + producerCount);
        System.out.println("Studios loaded: " + studioCount);

        // Assert that data has been loaded
        assert movieCount > 0 : "Movies should have been loaded";
        assert producerCount > 0 : "Producers should have been loaded";
        assert studioCount > 0 : "Studios should have been loaded";

        // check for specific data - First in line from .csv
        Optional<Movie> movie = movieRepository.findByTitle("Can't Stop the Music");
        assert movie.isPresent() : "Specific movie should exist";

        // Check associations
        assert !movie.get().getProducers().isEmpty() : "Movie should have producers";
        assert !movie.get().getStudios().isEmpty() : "Movie should have studios";
    }
}