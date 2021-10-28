package com.example.apiDisney.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="movies")
public class MovieEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String title;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date creationdate;
    @Column(nullable = false)
    private int clasification;

    @JsonIgnore
    @ManyToMany(mappedBy = "movieEntities",fetch = FetchType.EAGER)
    private Set<CharacterEntity> characterEntities = new HashSet<CharacterEntity>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    @JoinTable(
            name="movie_genders",
            joinColumns =  @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name= "gender_id"))
    private Set<GenderEntity> genderEntities = new HashSet<GenderEntity>();

    public MovieEntity(String image, String title, Date creationdate, int clasification) {
        this.image = image;
        this.title = title;
        this.creationdate = creationdate;
        this.clasification = clasification;
    }

    public void addCharacter(CharacterEntity characterEntity){
        this.characterEntities.add(characterEntity);
         characterEntity.getMovieEntities().add(this);
    }
    public void removeCharacter(CharacterEntity characterEntity){

        this.characterEntities.remove(characterEntity);

        System.out.println("cantida de personajes en una pelicuala: "+this.characterEntities.size());
        characterEntity.getMovieEntities().remove(this);
        System.out.println("cantiad de peliculas en las que participa un personaje: "+characterEntity.getMovieEntities().size());

    }

    public void addGender(GenderEntity genderEntity) {
        this.genderEntities.add(genderEntity);
        genderEntity.getMovieEntities().add(this);
    }
    public void removeGender(GenderEntity genderEntity){
        this.genderEntities.remove(genderEntity);
        genderEntity.getMovieEntities().remove(this);
    }
}
