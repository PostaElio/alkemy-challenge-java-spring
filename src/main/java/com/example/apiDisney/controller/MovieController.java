package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.*;
import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.service.MovieService;
import com.example.apiDisney.service.exception.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody MovieRequest movieRequest) throws Exception{
        try{
            movieService.save(new ModelMapper().map(movieRequest, MovieEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Movie added successfully"), HttpStatus.CREATED);
        }catch (CustomException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,ex.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody MovieRequest movieRequest) {
        try {
            movieService.update(id,new ModelMapper().map(movieRequest, MovieEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Movie update successfully"), HttpStatus.OK);
        }catch (CustomException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with id " + id + " does not exist in the database"), HttpStatus.NOT_FOUND);
        }
        catch (DataIntegrityViolationException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(movieService.findById(id), MovieDetailResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with id " + id + " does not exist in the database"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<MovieCompactResponse>> getMoviesCompact() {
        List<MovieCompactResponse> movieCompactResponses = movieService.getMovies()
                .stream().map(movieEntity -> new MovieCompactResponse(
                        movieEntity.getImage(),
                        movieEntity.getTitle(),
                        movieEntity.getCreationdate()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(movieCompactResponses, HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<List<MovieDetailResponse>> getCharactersDetail() {
        return new ResponseEntity<>(toMoviesDetailResponse(movieService.getMovies()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id) {
        try {
            movieService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Movie with id " + id + " is deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with id " + id + " does not exist in the database"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll() {
        movieService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "All Movies are deleted"), HttpStatus.OK);
    }
    /**********-- QUERY - MOVIE --************/
    @GetMapping(params = "name")
    public ResponseEntity<List<MovieDetailResponse>> getByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(toMoviesDetailResponse(movieService.getByTitle(name)), HttpStatus.OK);
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<MovieDetailResponse>> getByIdGenre(@RequestParam("genre") Long genre) {
        return new ResponseEntity<>(toMoviesDetailResponse(movieService.getByIdGenre(genre)), HttpStatus.OK);
    }

    @GetMapping(params = "order")
    public ResponseEntity<?> getMoviesSorted(@RequestParam("order") String order) {
        String orderUpper = order.toUpperCase();
        if (orderUpper.equals("ASC")) {
            return new ResponseEntity<>(toMoviesDetailResponse(movieService.findAllByOOrderByCration_dateAsc()), HttpStatus.OK);
        } else if (orderUpper.equals("DESC")) {
            return new ResponseEntity<>(toMoviesDetailResponse(movieService.findAllByOOrderByCration_dateDesc()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Enter an order ASC | DESC"), HttpStatus.BAD_REQUEST);
        }
    }
    /**********-- add charecter or gender --************/
    @PutMapping("/{movieId}/character/{characterId}")
    public ResponseEntity<ApiResponse> addCharacterToMovie(@PathVariable Long movieId, @PathVariable Long characterId) {
        try {
            movieService.addCharacterInMovie(movieId,characterId);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character added to movie"),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"The id's entered are incorrect or the movie already contains the character"),HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{movieId}/gender/{genderId}")
    public ResponseEntity<ApiResponse> addGenderToMovie(@PathVariable Long movieId, @PathVariable Long genderId) {
        try {
            movieService.addGenderInMovie(movieId,genderId);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender added to movie"),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"The id's entered are incorrect or the movie already contains the gender"),HttpStatus.NOT_FOUND);
        }
    }
    /**********-- HELP --************/
    private List<MovieDetailResponse> toMoviesDetailResponse(List<MovieEntity> movieEntities) {
        ModelMapper modelMapper = new ModelMapper();
        List<MovieDetailResponse> movieDetailResponses = movieEntities
                .stream().map(movieEntity ->
                        modelMapper.map(movieEntity, MovieDetailResponse.class))
                .collect(Collectors.toList());
        return movieDetailResponses;
    }
}
