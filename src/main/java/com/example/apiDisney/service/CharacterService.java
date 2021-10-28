package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface CharacterService {

    void update(Long id, CharacterEntity characterEntity) throws DataIntegrityViolationException, EmptyResultDataAccessException;
//spring.datasource.url=jdbc:postgresql://localhost:1000/disney-data
    /*Crud*/
    CharacterEntity save(CharacterEntity characterEntity) throws Exception;
    CharacterEntity findById(Long characterId) throws Exception;
    List<CharacterEntity> getCharacters();
    void deleteById(Long characterId) throws EmptyResultDataAccessException;
    void deleteAll();

    /*Queries*/
    List<CharacterEntity> getByName(String name);
    List<CharacterEntity> getByAge(int age);
    List<CharacterEntity> getByIdMovie(Long idMovie);

}
