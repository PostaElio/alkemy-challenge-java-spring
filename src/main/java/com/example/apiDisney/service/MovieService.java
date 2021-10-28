package com.example.apiDisney.service;

import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.service.exception.CustomException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.NoSuchElementException;

public interface MovieService {

    /*Crud*/
    MovieEntity save(MovieEntity movieEntity) throws CustomException,DataIntegrityViolationException;
    void update(Long id, MovieEntity movieEntity) throws EmptyResultDataAccessException, DataIntegrityViolationException, CustomException;
    MovieEntity findById(Long movieId) throws Exception;
    List<MovieEntity> getMovies();
    void deleteById(Long movieId) throws CustomException, NoSuchElementException;
    void deleteAll();

    /*Queries*/
    List<MovieEntity> getByTitle(String name);
    List<MovieEntity> getByIdGenre(Long idGenre);
    List<MovieEntity> findAllByOOrderByCration_dateDesc();
    List<MovieEntity> findAllByOOrderByCration_dateAsc();

    /*Set Entities*/
    MovieEntity addCharacterInMovie(Long movie_id, Long character_id) throws InvalidDataAccessApiUsageException,EmptyResultDataAccessException, CustomException;
    MovieEntity addGenderInMovie(Long movie_id, Long gender_id) throws InvalidDataAccessApiUsageException,EmptyResultDataAccessException;


    MovieEntity removeCharacter(Long movieId, Long characterId) throws CustomException;
}
