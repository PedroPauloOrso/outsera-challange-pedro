package com.pedro.orso.outsera.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.domain.Producer;
import com.pedro.orso.outsera.repository.MovieRepository;
import com.pedro.orso.outsera.repository.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Integration test for the AwardResource
@SpringBootTest
@AutoConfigureMockMvc
public class AwardResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    @Transactional
    public void setup() {
        // Clean up repositories
        movieRepository.deleteAll();
        producerRepository.deleteAll();

        // Create producers
        Producer producer1 = new Producer();
        producer1.setName("Producer 1");
        producerRepository.save(producer1);

        Producer producer2 = new Producer();
        producer2.setName("Producer 2");
        producerRepository.save(producer2);

        // Create movies and associate with producers
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setReleaseYear(2000);
        movie1.setWinner(true);
        movie1.setProducers(List.of(producer1));
        movieRepository.save(movie1);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setReleaseYear(2005);
        movie2.setWinner(true);
        movie2.setProducers(List.of(producer1));
        movieRepository.save(movie2);

        Movie movie3 = new Movie();
        movie3.setTitle("Movie 3");
        movie3.setReleaseYear(2002);
        movie3.setWinner(true);
        movie3.setProducers(List.of(producer2));
        movieRepository.save(movie3);

        Movie movie4 = new Movie();
        movie4.setTitle("Movie 4");
        movie4.setReleaseYear(2010);
        movie4.setWinner(true);
        movie4.setProducers(List.of(producer2));
        movieRepository.save(movie4);
    }

    @Test
    public void testGetProducersWithMinAndMaxInterval() throws Exception {
        mockMvc.perform(get("/v1/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min", hasSize(1)))
                .andExpect(jsonPath("$.max", hasSize(1)))
                .andExpect(jsonPath("$.min[0].producer", is("Producer 1")))
                .andExpect(jsonPath("$.min[0].interval", is(5)))
                .andExpect(jsonPath("$.min[0].previousWin", is(2000)))
                .andExpect(jsonPath("$.min[0].followingWin", is(2005)))
                .andExpect(jsonPath("$.max[0].producer", is("Producer 2")))
                .andExpect(jsonPath("$.max[0].interval", is(8)))
                .andExpect(jsonPath("$.max[0].previousWin", is(2002)))
                .andExpect(jsonPath("$.max[0].followingWin", is(2010)));
    }
}