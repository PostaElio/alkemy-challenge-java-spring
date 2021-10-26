package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.ApiResponse;
import com.example.apiDisney.controller.payload.MovieCompactResponse;
import com.example.apiDisney.controller.payload.MovieDetailResponse;
import com.example.apiDisney.controller.payload.MovieRequest;
import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            ModelMapper modelMapper = new ModelMapper();
            movieService.save(modelMapper.map(movieRequest, MovieEntity.class));
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with title and  creation date exists in database"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Movie added successfully"), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(movieService.findById(id), MovieDetailResponse.class), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with id " + id + " does not exist in the database"), HttpStatus.BAD_REQUEST);
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


    /**********-- DELETE BY ID - MOVIE --************/
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id) {
        try {
            movieService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Movie with id " + id + " is deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Movie with id " + id + " does not exist in the database"), HttpStatus.BAD_REQUEST);
        }
    }

    /**********-- DELETE ALL - MOVIE --************/
    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll() {
        movieService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "All Movies are deleted"), HttpStatus.OK);
    }

    /**********-- QUERY - MOVIE --************/

    @GetMapping(params = "name")
    public ResponseEntity<List<MovieDetailResponse>> getByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(toMoviesDetailResponse(movieService.getByName(name)), HttpStatus.OK);
    }

    @GetMapping(params = "genre")
    public ResponseEntity<List<MovieDetailResponse>> getByIdGenre(@RequestParam("genre") Long genre) {
        return new ResponseEntity<>(toMoviesDetailResponse(movieService.getByIdGenre(genre)), HttpStatus.OK);
    }

    @GetMapping(params = "order")
    public ResponseEntity<?> getMoviesSorted(@RequestParam("order") String order) throws Exception {
        String orderUpper = order.toUpperCase();
        if (orderUpper.equals("ASC")) {
            return new ResponseEntity<>(toMoviesDetailResponse(movieService.findAllByOOrderByCration_dateAsc()), HttpStatus.OK);
        } else if (orderUpper.equals("DESC")) {
            return new ResponseEntity<>(toMoviesDetailResponse(movieService.findAllByOOrderByCration_dateDesc()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Enter an order ASC | DESC"), HttpStatus.BAD_REQUEST);
        }
    }

    private List<MovieDetailResponse> toMoviesDetailResponse(List<MovieEntity> movieEntities) {
        ModelMapper modelMapper = new ModelMapper();
        List<MovieDetailResponse> movieDetailResponses = movieEntities
                .stream().map(movieEntity ->
                        modelMapper.map(movieEntity, MovieDetailResponse.class))
                .collect(Collectors.toList());
        return movieDetailResponses;
    }
    /**********-- add charecter or gender --************/
    @PutMapping("/{movieId}/character/{characterId}")
    public ResponseEntity<ApiResponse> addCharacterToMovie(@PathVariable Long movieId, @PathVariable Long characterId) {
        try {
            movieService.setCharacterInMovie(movieId,characterId);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character added to movie"),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"The id's entered are incorrect or the movie already contains the character"),HttpStatus.OK);
        }
    }
    @PutMapping("/{movieId}/gender/{genderId}")
    public ResponseEntity<ApiResponse> addGenderToMovie(@PathVariable Long movieId, @PathVariable Long genderId) {
        try {
            movieService.setGenderInMovie(movieId,genderId);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender added to movie"),HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"The id's entered are incorrect or the movie already contains the gender"),HttpStatus.OK);
        }
    }
}
