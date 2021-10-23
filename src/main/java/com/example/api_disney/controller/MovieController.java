package com.example.api_disney.controller;

import com.example.api_disney.controller.payload.ApiResponse;
import com.example.api_disney.controller.payload.MovieCompactResponse;
import com.example.api_disney.controller.payload.MovieDetailResponse;
import com.example.api_disney.controller.payload.MovieRequest;
import com.example.api_disney.model.MovieEntity;
import com.example.api_disney.service.MovieService;
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
    /**********-- SAVE MOVIE - UPDATE + ID in Json-************/
    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody MovieRequest movieRequest){
        ModelMapper modelMapper = new ModelMapper();
        movieService.save(modelMapper.map(movieRequest,MovieEntity.class));
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Movie added successfully"),HttpStatus.OK);
    }

    /**********-- GET - MOVIE COMPACT --************/
    @GetMapping()
    public ResponseEntity<List<MovieCompactResponse>> getMoviesCompact(){
        List<MovieCompactResponse> movieCompactResponses = movieService.getMovies()
                .stream().map(movieEntity -> new MovieCompactResponse(
                        movieEntity.getImage(),
                        movieEntity.getTitle(),
                        movieEntity.getCreationdate()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(movieCompactResponses,HttpStatus.OK);
    }
    /**********-- GET - MOVIE DETAILS --************/
    @GetMapping("/details")
    public ResponseEntity<List<MovieDetailResponse>> getCharactersDetail(){
        ModelMapper modelMapper = new ModelMapper();
        List<MovieDetailResponse> movieDetailResponses = movieService.getMovies()
                .stream().map(movieEntity ->
                        modelMapper.map(movieEntity,MovieDetailResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(movieDetailResponses, HttpStatus.OK);
    }
    /**********-- DELETE BY ID - MOVIE --************/
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id) {
        try {
            movieService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Movie with id "+id+" is deleted"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Movie with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);
        }
    }
    /**********-- DELETE ALL - MOVIE --************/
    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        movieService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Movies are deleted"),HttpStatus.OK);
    }
    /**********-- QUERY - MOVIE --************/

    @GetMapping("/name")
    public ResponseEntity<List<MovieEntity>> getByName(@RequestParam("name") String name){
        return new ResponseEntity<>(movieService.getByName(name),HttpStatus.OK);
    }
    @GetMapping("/genre")
    public ResponseEntity<List<MovieEntity>> getByIdGenre(@RequestParam("idGenre") Long idGenre){
        return new ResponseEntity<>(movieService.getByIdGenre(idGenre),HttpStatus.OK);
    }
    @GetMapping("/order")
    public ResponseEntity<List<MovieEntity>> getMoviesSorted(@RequestParam("order") String order) throws Exception {
        String orderUpper = order.toUpperCase();
        if(orderUpper.equals("ASC")){
            return new ResponseEntity<>(movieService.findAllByOOrderByCration_dateAsc(),HttpStatus.OK);
        }else if(orderUpper.equals("DESC")){
            return new ResponseEntity<>(movieService.findAllByOOrderByCration_dateDesc(),HttpStatus.OK);
        }else{
            throw new Exception("Enter an order ASC | DESC");
        }

    }


    /*
    /movies?name=nombre
    /movies?genre=idGenero
    /movies?order=ASC | DESC
     */

}
