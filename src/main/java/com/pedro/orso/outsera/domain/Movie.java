package com.pedro.orso.outsera.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    @Size(max = 500)
    private String title;

    @Column(name = "release_year", nullable = false)
    private int releaseYear;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;

    @ManyToMany
    @JoinTable(
            name = "movie_studio",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private List<Studio> studios;

    @ManyToMany
    @JoinTable(
            name = "movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private List<Producer> producers;

    private boolean winner;

}

