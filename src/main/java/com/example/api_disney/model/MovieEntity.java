package com.example.api_disney.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="movies")
public class MovieEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private String image;
    private String title;
    private Date creationdate;
    private int clasification;

    //Solo necesito que se guarden las categorias y que se actualicen, no voy a necesitar que cuando se elimine
    //una pelicula tambien se elimine todos sus personajes
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="movie_characters",
            joinColumns =  @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name= "character_id"))
    private List<CharacterEntity> characterEntities = new ArrayList<CharacterEntity>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="movie_genders",
            joinColumns =  @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name= "gender_id"))
    private List<GenderEntity> genderEntities = new ArrayList<GenderEntity>();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public int getClasification() {
        return clasification;
    }

    public void setClasification(int clasification) {
        this.clasification = clasification;
    }

    public List<CharacterEntity> getCharacterEntities() {
        return characterEntities;
    }

    public void setCharacterEntities(List<CharacterEntity> characterEntities) {
        this.characterEntities = characterEntities;
    }

    public List<GenderEntity> getGenderEntities() {
        return genderEntities;
    }

    public void setGenderEntities(List<GenderEntity> genderEntities) {
        this.genderEntities = genderEntities;
    }
}
