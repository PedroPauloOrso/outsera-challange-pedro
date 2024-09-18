package com.pedro.orso.outsera.service;

import com.pedro.orso.outsera.domain.Movie;
import com.pedro.orso.outsera.repository.MovieRepository;
import com.pedro.orso.outsera.repository.ProducerRepository;
import com.pedro.orso.outsera.repository.StudioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DataLoaderIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void testDataLoaderEnsuresCorrectDataThroughAPI() throws Exception {
        // Checking if the first set of movies matches
        // TODO Should I add more?
        mockMvc.perform(get("/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)))

                .andExpect(jsonPath("$[0].title").value("3000 Miles to Graceland"))
                .andExpect(jsonPath("$[0].releaseYear").value(2001))
                .andExpect(jsonPath("$[0].producers[0].name").value("Demian Lichtenstein"))
                .andExpect(jsonPath("$[0].studios[0].name").value("Warner Bros."))

                .andExpect(jsonPath("$[1].title").value("A Madea Christmas"))
                .andExpect(jsonPath("$[1].releaseYear").value(2013))
                .andExpect(jsonPath("$[1].producers[0].name").value("Ozzie Areu"))
                .andExpect(jsonPath("$[1].studios[0].name").value("Lionsgate"))

                .andExpect(jsonPath("$[2].title").value("A Madea Family Funeral"))
                .andExpect(jsonPath("$[2].releaseYear").value(2019))
                .andExpect(jsonPath("$[2].producers[0].name").value("Ozzie Areu"))
                .andExpect(jsonPath("$[2].studios[0].name").value("Lionsgate"));
    }
}