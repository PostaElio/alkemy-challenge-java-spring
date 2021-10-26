package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.repository.MovieRepository;
import com.example.apiDisney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterServiceImpl characterService;

    @Autowired
    private GenderServiceImpl genderService;

    public MovieEntity save(MovieEntity movieEntity) throws Exception {
        boolean exists = movieRepository.getByTitle(movieEntity.getTitle())
                .stream().map(movie ->
                        movie.getCreationdate().equals(movieEntity.getCreationdate()))
                .reduce(false,(x,y) -> x || y);
        if(exists){
            throw new Exception("Movie with title and  creation date exists in database");
        }
        try {
            return movieRepository.save(movieEntity);
        }catch (Exception ex){
            throw new Exception("Enter corrects properties");
        }
    }

    public MovieEntity update(MovieEntity movieEntity) throws Exception{
        return movieRepository.save(movieEntity);
    }

    public List<MovieEntity> getMovies(){
        return movieRepository.findAll();
    }

    //No puede eliminarse
    //El id es cualquier cosa
    public void deleteById(Long id) throws Exception {
        try {
            movieRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EmptyResultDataAccessException("Movie with id " + id + " not found in database", 0);
        }
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

    public MovieEntity setCharacterInMovie(Long movie_id, Long character_id) throws Exception {
        try{
            MovieEntity movieEntity = this.findById(movie_id);
            CharacterEntity characterEntity = characterService.findById(character_id);
            if (movieEntity.getCharacterEntities().contains(characterEntity)){
                throw new Exception("This Movie contains character "+characterEntity.getName());
            }else{
                movieEntity.addCharacter(characterEntity);
                return movieRepository.save(movieEntity);
            }
        }catch (Exception ex){
            throw new Exception("Enter corrects id's for movie or Gdender");
        }
    }

    public MovieEntity setGenderInMovie(Long movie_id, Long gender_id) throws Exception{

        try{
            MovieEntity movieEntity = this.findById(movie_id);
            GenderEntity genderEntity = genderService.findById(gender_id);
            if (movieEntity.getGenderEntities().contains(genderEntity)){
                throw new Exception("This Movie contains gender "+genderEntity.getName());
            }else{
                movieEntity.addGender(genderEntity);
                return movieRepository.save(movieEntity);
            }
        }catch (Exception ex){
            throw new Exception("Enter corrects id's for movie or Gdender");
        }
    }

    public MovieEntity findById(Long id) throws Exception{
        return movieRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));
    }

}
