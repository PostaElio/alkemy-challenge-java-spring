package com.example.apiDisney.model;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name="genders")
public class GenderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(name = "title")
    @ManyToMany(mappedBy = "genderEntities", fetch = FetchType.EAGER)
    private Set<MovieEntity> movieEntities = new HashSet<MovieEntity>();

    public GenderEntity(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<MovieEntity> getMovieEntities() {
        return movieEntities;
    }

    public void setMovieEntities(Set<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }
}
