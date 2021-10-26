package com.example.apiDisney.service;

import com.example.apiDisney.model.MovieEntity;

import java.util.List;

public interface MovieService {

    /*Crud*/
    MovieEntity save(MovieEntity movieEntity) throws Exception;
    MovieEntity findById(Long id) throws Exception;
    List<MovieEntity> getMovies();
    void deleteById(Long id) throws Exception;
    void deleteAll();

    /*Queries*/
    List<MovieEntity> getByName(String name);
    List<MovieEntity> getByIdGenre(Long idGenre);
    List<MovieEntity> findAllByOOrderByCration_dateDesc();
    List<MovieEntity> findAllByOOrderByCration_dateAsc();

    /*Set Entities*/
    MovieEntity setCharacterInMovie(Long movie_id, Long character_id) throws Exception;
    MovieEntity setGenderInMovie(Long movie_id, Long gender_id) throws Exception;

}
