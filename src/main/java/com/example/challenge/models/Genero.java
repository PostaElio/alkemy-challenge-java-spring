package com.example.challenge.models;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="genders")
public class Genero {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    @Column(unique = true,nullable = false)
    private String nombre;
    private String imagen;
    @Column(name = "titles")
    @OneToMany(fetch = FetchType.EAGER)
    private List<MovieModel> peliculaOSerieAsociadas;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }
}
