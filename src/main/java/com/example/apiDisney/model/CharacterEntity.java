package com.example.apiDisney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name="characters")
public class CharacterEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private float weight;
    @Column(nullable = false)
    private String history;
    @JsonIgnore


    @ManyToMany(mappedBy = "characterEntities",fetch = FetchType.EAGER)

    /*
    @ManyToMany(fetch = FetchType.EAGER, cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="movie_characters",
            joinColumns =  @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name= "movie_id"))

     */
    private Set<MovieEntity> movieEntities = new HashSet<MovieEntity>();

    public CharacterEntity(String image, String name, int age, float weight, String history) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.history = history;
    }
    public void removeAllMovies(){
        this.movieEntities = new HashSet<MovieEntity>();
    }

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

    public Set<MovieEntity> getMovieEntities() {
        return movieEntities;
    }

    public void setMovieEntities(Set<MovieEntity> movieEntities) {
        this.movieEntities = movieEntities;
    }
}
