package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.repository.MovieRepository;
import com.example.apiDisney.service.MovieService;
import com.example.apiDisney.service.exception.CustomException;
import javassist.bytecode.ExceptionsAttribute;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterServiceImpl characterService;

    @Autowired
    private GenderServiceImpl genderService;

    public MovieEntity save(MovieEntity movieEntity) throws CustomException,DataIntegrityViolationException{
        if(movieRepository.existsByTitleAndCreationdate1(movieEntity.getTitle(),movieEntity.getCreationdate())){
            throw new CustomException("Movie with title and  creation date exists in database");
        }
       try {
            return movieRepository.save(movieEntity);
        }catch (DataIntegrityViolationException ex) {
           throw new DataIntegrityViolationException("Enter corrects properties");
       }
    }

    @Override
    public void update(Long id, MovieEntity movieEntity) throws EmptyResultDataAccessException, DataIntegrityViolationException, CustomException{
        movieEntity.setId(findById(id).getId());

        if(movieRepository.existsByTitleAndCreationdate1(movieEntity.getTitle(),movieEntity.getCreationdate())) {
            //Se testea en trySaveMovieWithTitleAndCreationDateExists()
            throw new CustomException("Movie with title and  creation date exists in database");
        }else{
            movieRepository.save(movieEntity);
        }
    }

    public MovieEntity findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Entity with id "+id+" not found.",0));
    }

    public List<MovieEntity> getMovies(){
        return movieRepository.findAll();
    }
//EmptyResultDataAccessException ,
    public void deleteById(Long id) throws CustomException,NoSuchElementException{
        try {
            if (movieRepository.findById(id).get().getCharacterEntities().isEmpty()) {
                movieRepository.deleteById(id);
            } else {
                throw new CustomException("Movie has charecters asigned,try deleted first the characters for delete this movie");
            }
            //testeado en tryDeleteByIdNotExisting()
        }catch (NoSuchElementException ex){
            throw new NoSuchElementException("Movie with id " + id + " not found in database");
        }
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public List<MovieEntity> getByTitle(String name) {
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

    public MovieEntity addCharacterInMovie(Long movie_id, Long character_id) throws InvalidDataAccessApiUsageException,EmptyResultDataAccessException, CustomException{
        try{
            MovieEntity movieEntity = this.findById(movie_id);
            CharacterEntity characterEntity = characterService.findById(character_id);
            if (movieEntity.getCharacterEntities().contains(characterEntity)){
                throw new InvalidDataAccessApiUsageException("This Movie contains character "+characterEntity.getName());
            }else{
                movieEntity.addCharacter(characterEntity);
                characterService.update(characterEntity.getId(),characterEntity);
                return movieRepository.save(movieEntity);
            }
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Enter corrects id for movie or Gdender",0);
        }
    }

    public MovieEntity addGenderInMovie(Long movie_id, Long gender_id) throws InvalidDataAccessApiUsageException, EmptyResultDataAccessException{
        try{
            MovieEntity movieEntity = this.findById(movie_id);
            GenderEntity genderEntity = genderService.findById(gender_id);
            if (movieEntity.getGenderEntities().contains(genderEntity)){
                /*This case is tested "tryAddAGenderIncludeInMovie()"*/
                throw new InvalidDataAccessApiUsageException("This Movie contains gender "+genderEntity.getName());
            }else{
                movieEntity.addGender(genderEntity);
                return movieRepository.save(movieEntity);
            }
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Enter corrects id's for movie or Gdender",0);
        }
    }

    @Override
    public MovieEntity removeCharacter(Long movieId, Long characterId) throws CustomException{
        try{
            MovieEntity movieEntity = this.findById(movieId);
            CharacterEntity characterEntity = characterService.findById(characterId);
            if (movieEntity.getCharacterEntities().contains(characterEntity)){
                throw new CustomException("This Movie NO contains character "+characterEntity.getName());
            }else{
                movieEntity.removeCharacter(characterEntity);
                characterService.save(characterEntity);
                return movieRepository.save(movieEntity);
            }
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Enter corrects id for movie or Gdender",0);
        }
    }

}
