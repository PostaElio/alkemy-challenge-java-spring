package com.example.api_disney.service;

import com.example.api_disney.controller.payload.MovieDetailResponse;
import com.example.api_disney.model.MovieEntity;
import com.example.api_disney.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public MovieEntity save(MovieEntity movieEntity) {
        return movieRepository.save(movieEntity);
    }

    public List<MovieEntity> getMovies(){
        return (List<MovieEntity>) movieRepository.findAll();
    }


    public List<MovieDetailResponse> getMoviesDetailDTO() {
        ModelMapper modelMapper = new ModelMapper();
        List<MovieEntity> movieEntityList = movieRepository.findAll();
        return movieEntityList.stream()
                .map(movieEntity ->
                        modelMapper.map(movieEntity, MovieDetailResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public List<MovieEntity> getByName(String name) {
        return movieRepository.getByTitle(name);
    }

    public List<MovieEntity> getByIdGenre(Long idGenre) {
        return movieRepository.getByIdGenre(idGenre);
    }


    public List<MovieEntity> findAllByOOrderByCration_dateDesc() {
        return movieRepository.findByOrderByCreationdateDesc();
    }

    public List<MovieEntity> findAllByOOrderByCration_dateAsc() {
        return movieRepository.findByOrderByCreationdateAsc();
    }

}
