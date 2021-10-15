package com.example.challenge.controlador;

import com.example.challenge.models.PersonajeModel;
import com.example.challenge.services.PersonajeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("characters")
public class PersonajesController {

    private PersonajeService personajeService;

    @GetMapping()
    public ResponseEntity<List<PersonajeModel>> getCharacters(){
        return new ResponseEntity<>(personajeService.getCharacters(), HttpStatus.OK);
    }

}
