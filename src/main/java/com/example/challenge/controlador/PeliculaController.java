package com.example.challenge.controlador;

import com.example.challenge.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/movies")
public class PeliculaController {

    @Autowired
    private MovieRepository movieRepository;
}
