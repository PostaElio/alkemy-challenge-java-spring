package com.example.challenge.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name="filmes")
public class MovieModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    private Long id;
    private String imagen;
    @Column(unique = true,nullable = false)
    private String titulo;
    private Date fechaDeCreacion;
    private int clasificacion;
    @OneToMany(fetch = FetchType.EAGER)
    private List<PersonajeModel> personajesAsociados;

    public Long getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public Date getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public int getClasificacion() {
        return clasificacion;
    }


}
