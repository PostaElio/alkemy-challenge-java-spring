package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface CharacterService {
//spring.datasource.url=jdbc:postgresql://localhost:1000/disney-data
    /*Crud*/
    CharacterEntity save(CharacterEntity characterEntity) throws Exception;
    CharacterEntity findById(Long character_id) throws Exception;
    List<CharacterEntity> getCharacters();
    void deleteById(Long id) throws EmptyResultDataAccessException;
    void deleteAll();

    /*Queries*/
    List<CharacterEntity> getByName(String name);
    List<CharacterEntity> getByAge(int age);
    List<CharacterEntity> getByIdMovie(Long idMovie);

}
