package com.example.challenge.models;

import javax.persistence.*;


@Entity
@Table(name="characters")
public class PersonajeModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private String imagen;
    @Column(unique = true,nullable = false)
    private String Nombre;
    @Column(unique = true,nullable = false)
    private int edad;
    private float peso;
    private String historia;
    @Column(name = "pelicula-o-serie")
    @OneToOne(fetch = FetchType.EAGER)
    private MovieModel peliculaOSerieList;

    public String getImagen() {
        return imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getEdad() {
        return edad;
    }

    public float getPeso() {
        return peso;
    }

    public String getHistoria() {
        return historia;
    }
}
