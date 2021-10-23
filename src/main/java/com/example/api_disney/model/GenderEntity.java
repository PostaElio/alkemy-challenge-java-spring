package com.example.api_disney.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="genders")
public class GenderEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;
    private String image;
    @Column(name = "title")
    @ManyToMany(mappedBy = "genderEntities")
    private List<MovieEntity> movieEntities = new ArrayList<MovieEntity>();

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

    public List<MovieEntity> getMovieEntities() {
        return movieEntities;
    }

    public void setMovieEntities(List<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }
}
