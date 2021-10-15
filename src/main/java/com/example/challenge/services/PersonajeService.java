package com.example.challenge.services;

import com.example.challenge.models.PersonajeModel;
import com.example.challenge.repositories.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    public List<PersonajeModel> getCharacters() {
        return (List<PersonajeModel>) personajeRepository.findAll();
    }
}
