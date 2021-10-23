package com.example.api_disney.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="characters")
public class CharacterEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private String image;
    private String name;
    private int age;
    private float weight;
    private String history;

    @ManyToMany(mappedBy = "characterEntities")
    private List<MovieEntity> movieEntities = new ArrayList<MovieEntity>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public List<MovieEntity> getMovieEntities() {
        return movieEntities;
    }

    public void setMovieEntities(List<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }
}
